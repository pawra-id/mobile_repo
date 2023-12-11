package id.pawra.ui.screen.pet.profile

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.pawra.data.local.preference.PetData
import id.pawra.data.remote.response.PetResponseItem
import id.pawra.data.repository.AuthRepository
import id.pawra.data.repository.PetRepository
import id.pawra.ui.common.UiState
import id.pawra.utils.uriToFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class PetViewModel(
    private val petRepository: PetRepository,
    private val authRepository: AuthRepository,
    private val validateDogName: ValidateDogName = ValidateDogName(),
    private val validateDogBreed: ValidateDogBreed = ValidateDogBreed(),
    private val validateDogYear: ValidateDogYear = ValidateDogYear(),
    private val validateDogHeight: ValidateDogHeight = ValidateDogHeight(),
    private val validateDogWeight: ValidateDogWeight = ValidateDogWeight(),
    private val validateDogColor: ValidateDogColor = ValidateDogColor(),
    private val validateDogMicrochipId: ValidateDogMicrochipId = ValidateDogMicrochipId(),
    private val validateDogSummary: ValidateDogSummary = ValidateDogSummary(),
    private val validateDogImage: ValidateDogImage = ValidateDogImage()
): ViewModel() {

    private val _petState: MutableStateFlow<UiState<List<PetResponseItem>>> = MutableStateFlow(UiState.Loading)
    val petState: StateFlow<UiState<List<PetResponseItem>>>
        get() = _petState

    private val _petDetailState: MutableStateFlow<UiState<PetResponseItem>> = MutableStateFlow(UiState.Loading)
    val petDetailState: StateFlow<UiState<PetResponseItem>>
        get() = _petDetailState

    private val _petAddState: MutableStateFlow<UiState<PetResponseItem>> = MutableStateFlow(UiState.None)
    val petAddState: StateFlow<UiState<PetResponseItem>>
        get() = _petAddState

    private val _petListState: MutableStateFlow<List<MutableMap<*, *>>> = MutableStateFlow(
        listOf()
    )
    val petListState: StateFlow<List<MutableMap<*, *>>>
        get() = _petListState

    private val _petUpdateState: MutableStateFlow<UiState<PetResponseItem?>> = MutableStateFlow(UiState.None)
    val petUpdateState : StateFlow<UiState<PetResponseItem?>>
        get() = _petUpdateState

    private var petId by mutableIntStateOf(0)
    private var showResultDialog by mutableStateOf(false)

    fun getDog() {
        viewModelScope.launch {
            val user = authRepository.getSession().first()
            petRepository.getDog(user)
                .collect { dogs ->
                    when {
                        dogs.error != null ->{
                            _petState.value = UiState.Error(dogs.error)
                        }
                        else -> {
                            _petState.value = UiState.Success(dogs.items ?: listOf())
                        }
                    }
                }
        }
    }

    fun getDetailDog(petId: Int) {
        viewModelScope.launch {
            val user = authRepository.getSession().first()
            petRepository.getDetailDog(user, petId)
                .collect { dogDetail ->
                    when {
                        dogDetail.error != null ->{
                            _petDetailState.value = UiState.Error(dogDetail.error)
                        }
                        else -> {
                            _petDetailState.value = UiState.Success(dogDetail)
                            this@PetViewModel.petId = dogDetail.id
                        }
                    }
                }
        }
    }

    fun getListDogToAddDogForm(petId: Int) {
        viewModelScope.launch {
            val user = authRepository.getSession().first()
            val listDog = mutableListOf<MutableMap<*, *>>()
            if (petId != 0){
                petRepository.getDetailDog(user, petId)
                    .collect { dogDetail ->
                        when {
                            dogDetail.error != null ->{
                                _petListState.value = listDog
                            }
                            else -> {
                                listDog.add(mutableMapOf("id" to dogDetail.id, "name" to dogDetail.name))
                                _petListState.value = listDog
                            }
                        }
                    }
            } else {
                petRepository.getDog(user)
                    .collect { dogs ->
                        when {
                            dogs.error != null ->{
                                _petListState.value = listDog
                            }
                            else -> {
                                dogs.items?.forEach{ item ->
                                    listDog.add(mutableMapOf("id" to item.id, "name" to item.name))
                                }
                                _petListState.value = listDog
                            }
                        }
                    }
            }

        }
    }

    private fun addDog(
        name: String,
        breed: String,
        neutred: Boolean,
        age: Int,
        height: Int,
        gender: String,
        weight: Int,
        primaryColor: String,
        microchipId: String,
        summary: String,
        file: MultipartBody.Part
    ) {
        viewModelScope.launch {
            _petAddState.value = UiState.Loading
            val user = authRepository.getSession().first()
            petRepository.postDogImage(user, file)
                .catch {
                    _petAddState.value = UiState.Error(it.message.toString())
                }
                .collect { result ->
                    val petData = PetData(
                        name = name,
                        image = result,
                        breed = breed,
                        neutred = neutred,
                        age = age,
                        height = height,
                        gender = gender,
                        weight = weight,
                        primaryColor = primaryColor,
                        microchipId = microchipId,
                        summary = summary
                    )
                    petRepository.addDog(user, petData)
                        .collect { dogDetail ->
                            when {
                                dogDetail.error != null -> _petAddState.value = UiState.Error(dogDetail.error)
                                else -> _petAddState.value = UiState.Success(dogDetail)
                            }
                        }
                }
        }
    }

    var addDogValidationState by mutableStateOf(DogAddFormState())

    var showDialog by mutableStateOf(false)

    fun onEventAddDog(event: DogAddFormEvent, context: Context) {
        when(event) {
            is DogAddFormEvent.DogNameChanged -> {
                addDogValidationState = addDogValidationState.copy(name = event.name)
                val nameResult = validateDogName.execute(addDogValidationState.name)
                addDogValidationState = addDogValidationState.copy(
                    nameError = nameResult.errorMessage
                )
            }
            is DogAddFormEvent.DogBreedChanged -> {
                addDogValidationState = addDogValidationState.copy(breed = event.breed)
                val breedResult = validateDogBreed.execute(addDogValidationState.breed)
                addDogValidationState = addDogValidationState.copy(
                    breedError = breedResult.errorMessage
                )
            }
            is DogAddFormEvent.DogYearChanged -> {
                addDogValidationState = addDogValidationState.copy(year = event.year)
                val yearResult = validateDogYear.execute(addDogValidationState.year)
                addDogValidationState = addDogValidationState.copy(
                    yearError = yearResult.errorMessage
                )
            }
            is DogAddFormEvent.DogHeightChanged -> {
                addDogValidationState = addDogValidationState.copy(height = event.height)
                val heightResult = validateDogHeight.execute(addDogValidationState.height)
                addDogValidationState = addDogValidationState.copy(
                    heightError = heightResult.errorMessage
                )
            }
            is DogAddFormEvent.DogWeightChanged -> {
                addDogValidationState = addDogValidationState.copy(weight = event.weight)
                val weightResult = validateDogWeight.execute(addDogValidationState.weight)
                addDogValidationState = addDogValidationState.copy(
                    weightError = weightResult.errorMessage
                )
            }
            is DogAddFormEvent.DogColorChanged -> {
                addDogValidationState = addDogValidationState.copy(color = event.color)
                val colorResult = validateDogColor.execute(addDogValidationState.color)
                addDogValidationState = addDogValidationState.copy(
                    colorError = colorResult.errorMessage
                )
            }
            is DogAddFormEvent.DogMicrochipIdChanged -> {
                addDogValidationState = addDogValidationState.copy(microchipId = event.microchipId)
                val microchipIdResult = validateDogMicrochipId.execute(addDogValidationState.microchipId)
                addDogValidationState = addDogValidationState.copy(
                    microchipIdError = microchipIdResult.errorMessage
                )
            }
            is DogAddFormEvent.DogSummaryChanged -> {
                addDogValidationState = addDogValidationState.copy(summary = event.summary)
                val summaryResult = validateDogSummary.execute(addDogValidationState.summary)
                addDogValidationState = addDogValidationState.copy(
                    summaryError = summaryResult.errorMessage
                )
            }
            is DogAddFormEvent.DogSexChanged -> {
                addDogValidationState = addDogValidationState.copy(sex = event.sex)
            }
            is DogAddFormEvent.DogNeuteredChanged -> {
                addDogValidationState = addDogValidationState.copy(neutered = event.neutered)
            }
            is DogAddFormEvent.DogImageChanged -> {
                addDogValidationState = addDogValidationState.copy(file = event.file)
                val fileResult = validateDogImage.execute(addDogValidationState.file)
                addDogValidationState = addDogValidationState.copy(
                    fileError = fileResult.errorMessage
                )
            }
            is DogAddFormEvent.Submit -> {
                submitDataAddDog(context)
            }
        }
    }

    private fun submitDataAddDog(context: Context) {
        val nameResult = validateDogName.execute(addDogValidationState.name)
        val breedResult = validateDogBreed.execute(addDogValidationState.breed)
        val yearResult = validateDogYear.execute(addDogValidationState.year)
        val heightResult = validateDogHeight.execute(addDogValidationState.height)
        val weightResult = validateDogWeight.execute(addDogValidationState.weight)
        val colorResult = validateDogColor.execute(addDogValidationState.color)
        val microchipIdResult = validateDogMicrochipId.execute(addDogValidationState.microchipId)
        val summaryResult = validateDogSummary.execute(addDogValidationState.summary)
        val fileResult = validateDogImage.execute(addDogValidationState.file)

        val hasError = listOf(
            nameResult,
            breedResult,
            yearResult,
            heightResult,
            weightResult,
            colorResult,
            microchipIdResult,
            summaryResult,
            fileResult
        ).any { !it.successful }

        if(hasError) {
            addDogValidationState = addDogValidationState.copy(
                nameError = nameResult.errorMessage,
                breedError = breedResult.errorMessage,
                yearError = yearResult.errorMessage,
                heightError = heightResult.errorMessage,
                weightError = weightResult.errorMessage,
                colorError = colorResult.errorMessage,
                microchipIdError = microchipIdResult.errorMessage,
                summaryError = summaryResult.errorMessage,
                fileError = fileResult.errorMessage
            )
            showDialog = false
        } else {
            val imageFile = uriToFile(Uri.parse(addDogValidationState.file), context)
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "file",
                imageFile.name,
                requestImageFile
            )

            addDog(
                addDogValidationState.name,
                addDogValidationState.breed,
                addDogValidationState.neutered,
                addDogValidationState.year.toInt(),
                addDogValidationState.height.toInt(),
                addDogValidationState.sex,
                addDogValidationState.weight.toInt(),
                addDogValidationState.color,
                addDogValidationState.microchipId,
                addDogValidationState.summary,
                multipartBody
            )
            showDialog = true
        }
    }

    private fun updateDog(
        petId: Int,
        name: String,
        breed: String,
        neutered: Boolean,
        age: Int,
        height: Int,
        gender: String,
        weight: Int,
        primaryColor: String,
        microchipId: String,
        summary: String,
        imageUrl: String,
        file: MultipartBody.Part? = null
    ) {
        viewModelScope.launch {
            _petUpdateState.value = UiState.Loading
            try {
                val user = authRepository.getSession().first()

                var image = imageUrl
                if (file != null) {
                    authRepository.postProfileImage(user, file)
                        .collect { result ->
                            image = result
                        }
                }

                val petData = PetData(
                    name = name,
                    image = image,
                    breed = breed,
                    neutred = neutered,
                    age = age,
                    height = height,
                    gender = gender,
                    weight = weight,
                    primaryColor = primaryColor,
                    microchipId = microchipId,
                    summary = summary
                )
                val updatedPet = petRepository.updateDog(user = user, petId = petId, petData = petData).first()

                _petUpdateState.value = UiState.Success(updatedPet)
            } catch (e: Exception) {
                _petUpdateState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    var updateDogValidationState by mutableStateOf(DogUpdateFormState())
    fun onEventUpdateDog(event: DogUpdateFormEvent) {
        when(event) {
            is DogUpdateFormEvent.DogNameChanged -> {
                updateDogValidationState = updateDogValidationState.copy(name = event.name)
                val nameResult = validateDogName.execute(updateDogValidationState.name)
                updateDogValidationState = updateDogValidationState.copy(
                    nameError = nameResult.errorMessage
                )
            }
            is DogUpdateFormEvent.DogBreedChanged -> {
                updateDogValidationState = updateDogValidationState.copy(breed = event.breed)
                val breedResult = validateDogBreed.execute(updateDogValidationState.breed)
                updateDogValidationState = updateDogValidationState.copy(
                    breedError = breedResult.errorMessage
                )
            }
            is DogUpdateFormEvent.DogYearChanged -> {
                updateDogValidationState = updateDogValidationState.copy(year = event.year)
                val yearResult = validateDogYear.execute(updateDogValidationState.year)
                updateDogValidationState = updateDogValidationState.copy(
                    yearError = yearResult.errorMessage
                )
            }
            is DogUpdateFormEvent.DogHeightChanged -> {
                updateDogValidationState = updateDogValidationState.copy(height = event.height)
                val heightResult = validateDogHeight.execute(updateDogValidationState.height)
                updateDogValidationState = updateDogValidationState.copy(
                    heightError = heightResult.errorMessage
                )
            }
            is DogUpdateFormEvent.DogWeightChanged -> {
                updateDogValidationState = updateDogValidationState.copy(weight = event.weight)
                val weightResult = validateDogWeight.execute(updateDogValidationState.weight)
                updateDogValidationState = updateDogValidationState.copy(
                    weightError = weightResult.errorMessage
                )
            }
            is DogUpdateFormEvent.DogColorChanged -> {
                updateDogValidationState = updateDogValidationState.copy(color = event.color)
                val colorResult = validateDogColor.execute(updateDogValidationState.color)
                updateDogValidationState = updateDogValidationState.copy(
                    colorError = colorResult.errorMessage
                )
            }
            is DogUpdateFormEvent.DogMicrochipIdChanged -> {
                updateDogValidationState = updateDogValidationState.copy(microchipId = event.microchipId)
                val microchipIdResult = validateDogMicrochipId.execute(updateDogValidationState.microchipId)
                updateDogValidationState = updateDogValidationState.copy(
                    microchipIdError = microchipIdResult.errorMessage
                )
            }
            is DogUpdateFormEvent.DogSummaryChanged -> {
                updateDogValidationState = updateDogValidationState.copy(summary = event.summary)
                val summaryResult = validateDogSummary.execute(updateDogValidationState.summary)
                updateDogValidationState = updateDogValidationState.copy(
                    summaryError = summaryResult.errorMessage
                )
            }
            is DogUpdateFormEvent.DogSexChanged -> {
                updateDogValidationState = updateDogValidationState.copy(sex = event.sex)
            }
            is DogUpdateFormEvent.DogNeuteredChanged -> {
                updateDogValidationState = updateDogValidationState.copy(neutered = event.neutered)
            }
            is DogUpdateFormEvent.DogImageChanged -> {
                updateDogValidationState = updateDogValidationState.copy(file = event.file)
                val fileResult = validateDogImage.execute(updateDogValidationState.file)
                updateDogValidationState = updateDogValidationState.copy(
                    fileError = fileResult.errorMessage
                )
            }
        }
    }

    fun submitDataUpdateDog(multipartBody: MultipartBody.Part? = null) {
        val nameResult = validateDogName.execute(updateDogValidationState.name)
        val breedResult = validateDogBreed.execute(updateDogValidationState.breed)
        val yearResult = validateDogYear.execute(updateDogValidationState.year)
        val heightResult = validateDogHeight.execute(updateDogValidationState.height)
        val weightResult = validateDogWeight.execute(updateDogValidationState.weight)
        val colorResult = validateDogColor.execute(updateDogValidationState.color)
        val microchipIdResult = validateDogMicrochipId.execute(updateDogValidationState.microchipId)
        val summaryResult = validateDogSummary.execute(updateDogValidationState.summary)
        val fileResult = validateDogImage.execute(updateDogValidationState.file)

        val hasError = listOf(
            nameResult,
            breedResult,
            yearResult,
            heightResult,
            weightResult,
            colorResult,
            microchipIdResult,
            summaryResult,
            fileResult
        ).any { !it.successful }

        if(hasError) {
            updateDogValidationState = updateDogValidationState.copy(
                nameError = nameResult.errorMessage,
                breedError = breedResult.errorMessage,
                yearError = yearResult.errorMessage,
                heightError = heightResult.errorMessage,
                weightError = weightResult.errorMessage,
                colorError = colorResult.errorMessage,
                microchipIdError = microchipIdResult.errorMessage,
                summaryError = summaryResult.errorMessage,
                fileError = fileResult.errorMessage
            )
            showDialog = false
        } else {

            updateDog(
                petId = updateDogValidationState.id,
                name = updateDogValidationState.name,
                breed = updateDogValidationState.breed,
                neutered = updateDogValidationState.neutered,
                age = updateDogValidationState.year.toInt(),
                height = updateDogValidationState.height.toInt(),
                gender = updateDogValidationState.sex,
                weight = updateDogValidationState.weight.toInt(),
                primaryColor = updateDogValidationState.color,
                microchipId = updateDogValidationState.microchipId,
                summary = updateDogValidationState.summary,
                imageUrl = updateDogValidationState.file,
                file = multipartBody
            )
            showDialog = true
        }
    }

    fun deleteDogId(){
        deleteDog(petId)
    }

    private fun deleteDog(petId: Int) {
        viewModelScope.launch {
            val user = authRepository.getSession().first()
            try {
                petRepository.deleteDog(user, petId)
                    .collect { result ->
                        handleDelete(result, result.error, _petDetailState)
                        if (result.error == null) {
                            showResultDialog = true
                        }
                    }
            } catch (e: Exception) {
                _petDetailState.value = UiState.Error(e.message ?: "Terjadi kesalahan yang tidak diketahui")
            }
        }
    }

    private fun handleDelete(
        result: PetResponseItem,
        error: String?,
        stateFlow: MutableStateFlow<UiState<PetResponseItem>>
    ) {
        when {
            error != null -> {
                stateFlow.value = UiState.Error(error)
            }
            else -> {
                stateFlow.value = UiState.Success(result)
            }
        }
    }
}
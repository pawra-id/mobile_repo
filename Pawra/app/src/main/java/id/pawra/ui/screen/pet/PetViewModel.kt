package id.pawra.ui.screen.pet

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.pawra.data.local.preference.PetData
import id.pawra.data.remote.response.PetResponseItem
import id.pawra.data.repository.AuthRepository
import id.pawra.data.repository.PetRepository
import id.pawra.ui.common.UiState
import id.pawra.ui.common.uriToFile
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

    fun getDog() {
        viewModelScope.launch {
            val user = authRepository.getSession().first()
            petRepository.getDog(user)
                .collect { dogs ->
                    when {
                        dogs.error != null -> _petState.value = UiState.Error(dogs.error)
                        else -> _petState.value = UiState.Success(dogs.petResponse ?: listOf())
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
                        dogDetail.error != null -> _petDetailState.value = UiState.Error(dogDetail.error)
                        else -> _petDetailState.value = UiState.Success(dogDetail)
                    }
                }
        }
    }

    fun addDog(
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
}
package id.pawra.ui.screen.pet

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.pawra.data.local.preference.PetData
import id.pawra.data.remote.response.PetResponse
import id.pawra.data.remote.response.PetResponseItem
import id.pawra.data.remote.response.SignInResponse
import id.pawra.data.repository.AuthRepository
import id.pawra.data.repository.PetRepository
import id.pawra.ui.common.UiState
import id.pawra.ui.components.addpet.uriToFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class PetViewModel(
    private val petRepository: PetRepository,
    private val authRepository: AuthRepository,
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

    var resultUpload by mutableStateOf("")

    fun getDog() {
        viewModelScope.launch {
            val user = authRepository.getSession().first()
            petRepository.getDog(user)
                .catch {
                    _petState.value = UiState.Error(it.message.toString())
                }
                .collect { dogs ->
                    _petState.value = UiState.Success(dogs)
                }
        }
    }

    fun getDetailDog(petId: Int) {
        viewModelScope.launch {
            val user = authRepository.getSession().first()
            petRepository.getDetailDog(user, petId)
                .catch {
                    _petDetailState.value = UiState.Error(it.message.toString())
                }
                .collect { dogDetail ->
                    _petDetailState.value = UiState.Success(dogDetail)
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
                        .catch {
                            _petAddState.value = UiState.Error(it.message.toString())
                        }
                        .collect { dogDetail ->
                            _petAddState.value = UiState.Success(dogDetail)
                        }
                }
        }
    }

    fun postDogImage(file: MultipartBody.Part) {
        viewModelScope.launch {
            val user = authRepository.getSession().first()

        }
    }
}
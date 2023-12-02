package id.pawra.ui.screen.pet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.pawra.data.remote.response.PetResponse
import id.pawra.data.remote.response.PetResponseItem
import id.pawra.data.remote.response.SignInResponse
import id.pawra.data.repository.AuthRepository
import id.pawra.data.repository.PetRepository
import id.pawra.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PetViewModel(
    private val petRepository: PetRepository,
    private val authRepository: AuthRepository,
): ViewModel() {

    private val _petState: MutableStateFlow<UiState<List<PetResponseItem>>> = MutableStateFlow(UiState.None)
    val petState: StateFlow<UiState<List<PetResponseItem>>>
        get() = _petState

    fun getDog() {
        viewModelScope.launch {
            val user = authRepository.getSession().first()
            petRepository.getDog(user.token)
                .collect { dogs ->
                    _petState.value = dogs
                }
        }
    }
}
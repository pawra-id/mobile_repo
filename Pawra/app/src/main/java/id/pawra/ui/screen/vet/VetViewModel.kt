package id.pawra.ui.screen.vet

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.pawra.data.remote.response.VetResponseItem
import id.pawra.data.repository.AuthRepository
import id.pawra.data.repository.VetRepository
import id.pawra.ui.common.UiState
import id.pawra.ui.components.vets.FilterVets
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class VetViewModel(
    private val vetRepository: VetRepository,
    private val authRepository: AuthRepository,
): ViewModel() {

    private val _vetsState: MutableStateFlow<UiState<List<VetResponseItem>>> = MutableStateFlow(
        UiState.Loading)
    val vetsState: StateFlow<UiState<List<VetResponseItem>>>
        get() = _vetsState

    private val _vetDetailState: MutableStateFlow<UiState<VetResponseItem>> = MutableStateFlow(
        UiState.Loading)
    val vetDetailState: StateFlow<UiState<VetResponseItem>>
        get() = _vetDetailState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getVets(keyword: String) {
        _query.value = keyword
        viewModelScope.launch {
            val user = authRepository.getSession().first()
//            if (_query.value.isEmpty()) {
//                _vetsState.value = UiState.Loading
//            }
            vetRepository.getVets(user, keyword)
                .collect { activities ->
                    when {
                        activities.error != null ->{
                            _vetsState.value = UiState.Error(activities.error)
                        }
                        else -> {
                            _vetsState.value = UiState.Success(activities.items ?: listOf())
                        }
                    }
                }


        }
    }

    private fun filterVets(list: List<VetResponseItem>, filter: String): List<VetResponseItem>{
        return when(filter){
            FilterVets.Nearest.name -> {
                list.sortedByDescending { it.createdAt }
            }

            FilterVets.Experience.name -> {
                list.sortedByDescending { it.description }
            }

            else -> { list }
        }
    }

    fun getDetailVet(vetId: Int) {
        viewModelScope.launch {
            val user = authRepository.getSession().first()
            vetRepository.getDetailVet(user, vetId)
                .collect { vetDetail ->
                    when {
                        vetDetail.error != null ->{
                            _vetDetailState.value = UiState.Error(vetDetail.error)
                        }
                        else -> {
                            _vetDetailState.value = UiState.Success(vetDetail)
                        }
                    }
                }
        }
    }
}
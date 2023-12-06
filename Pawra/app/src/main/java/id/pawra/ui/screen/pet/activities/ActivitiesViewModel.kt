package id.pawra.ui.screen.pet.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.pawra.data.remote.response.ActivitiesResponseItem
import id.pawra.data.repository.AuthRepository
import id.pawra.data.repository.ActivitiesRepository
import id.pawra.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ActivitiesViewModel(
    private val activitiesRepository: ActivitiesRepository,
    private val authRepository: AuthRepository,
): ViewModel() {

    private val _activitiesState: MutableStateFlow<UiState<List<ActivitiesResponseItem>>> = MutableStateFlow(UiState.None)
    val activitiesState: StateFlow<UiState<List<ActivitiesResponseItem>>>
        get() = _activitiesState

    fun getActivities() {
        viewModelScope.launch {
            val user = authRepository.getSession().first()
            activitiesRepository.getActivities(user)
                .collect { activities ->
                    when {
                        activities.error != null ->{
                            _activitiesState.value = UiState.Error(activities.error)
                        }
                        else -> {
                            _activitiesState.value = UiState.Success(activities.activitiesResponse ?: listOf())
                        }
                    }
                }
        }
    }
}
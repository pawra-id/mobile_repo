package id.pawra.ui.screen.pet.activities

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.pawra.data.local.preference.ActivityData
import id.pawra.data.local.preference.Tags
import id.pawra.data.remote.response.ActivitiesResponseItem
import id.pawra.data.repository.AuthRepository
import id.pawra.data.repository.ActivitiesRepository
import id.pawra.ui.common.UiState
import id.pawra.ui.components.addactivities.ChipData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ActivitiesViewModel(
    private val activitiesRepository: ActivitiesRepository,
    private val authRepository: AuthRepository,
    private val validateDog: ValidateDog = ValidateDog(),
    private val validateActivity: ValidateActivity = ValidateActivity(),
    private val validateTags: ValidateTags = ValidateTags(),
): ViewModel() {

    private val _activitiesState: MutableStateFlow<UiState<List<ActivitiesResponseItem>>> = MutableStateFlow(UiState.None)
    val activitiesState: StateFlow<UiState<List<ActivitiesResponseItem>>>
        get() = _activitiesState

    private val _addActivityState: MutableStateFlow<UiState<ActivitiesResponseItem>> = MutableStateFlow(UiState.None)
    val addActivityState: StateFlow<UiState<ActivitiesResponseItem>>
        get() = _addActivityState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getActivities() {
        viewModelScope.launch {
            _addActivityState.value = UiState.Loading
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

    fun getSpecificActivities(petId: Int, keyword: String) {

        _query.value = keyword

        viewModelScope.launch {
            _addActivityState.value = UiState.Loading
            val user = authRepository.getSession().first()
            if (_query.value.isNotEmpty()) {
                activitiesRepository.getSpesificActivities(user, petId, keyword)
                    .collect { activities ->
                        when {
                            activities.error != null -> {
                                _activitiesState.value = UiState.Error(activities.error)
                            }
                            else -> {
                                _activitiesState.value = UiState.Success(activities.activitiesResponse ?: listOf())
                            }
                        }
                    }
            } else {
                activitiesRepository.getSpesificActivities(user, petId)
                    .collect { activities ->
                        when {
                            activities.error != null -> {
                                _activitiesState.value = UiState.Error(activities.error)
                            }

                            else -> {
                                _activitiesState.value =
                                    UiState.Success(activities.activitiesResponse ?: listOf())
                            }
                        }
                    }
            }
        }
    }

    private fun addActivity(
        description: String,
        dogId: Int,
        tags: List<Tags>
    ) {
        viewModelScope.launch {
            _addActivityState.value = UiState.Loading
            val user = authRepository.getSession().first()
            val activityData = ActivityData(
                description = description,
                dogId = dogId,
                tags = tags
            )
            activitiesRepository.addActivity(user, activityData)
                .collect { activity ->
                    when {
                        activity.error != null ->{
                            _addActivityState.value = UiState.Error(activity.error)
                        }
                        else -> {
                            _addActivityState.value = UiState.Success(activity)
                        }
                    }
                }
        }
    }

    var stateAddActivity by mutableStateOf(AddActivityFormState())

    var showDialog by mutableStateOf(false)
    val tagChip = mutableListOf(ChipData(""))

    fun onEventAddActivity(event: AddActivityFormEvent) {
        when(event) {
            is AddActivityFormEvent.DogChanged -> {
                stateAddActivity = stateAddActivity.copy(dog = event.dog)
                val dogResult = validateDog.execute(stateAddActivity.dog)
                stateAddActivity = stateAddActivity.copy(
                    dogError = dogResult.errorMessage
                )
            }
            is AddActivityFormEvent.ActivityChanged -> {
                stateAddActivity = stateAddActivity.copy(activity = event.activity)
                val activityResult = validateActivity.execute(stateAddActivity.activity)
                stateAddActivity = stateAddActivity.copy(
                    activityError = activityResult.errorMessage
                )
            }
            is AddActivityFormEvent.TagsChanged -> {
                stateAddActivity = stateAddActivity.copy(tags = event.tags)
                val tagsResult = validateTags.execute(tagChip)
                stateAddActivity = stateAddActivity.copy(
                    tagsError = tagsResult.errorMessage
                )
            }
            is AddActivityFormEvent.Submit -> {
                submitDataAddActivity()
            }
        }
    }

    private fun submitDataAddActivity() {
        val dogResult = validateDog.execute(stateAddActivity.dog)
        val activityResult = validateActivity.execute(stateAddActivity.activity)
        val tagsResult = validateTags.execute(tagChip)

        val hasError = listOf(
            dogResult,
            activityResult,
            tagsResult,
        ).any { !it.successful }

        if(hasError) {
            stateAddActivity = stateAddActivity.copy(
                dogError = dogResult.errorMessage,
                activityError = activityResult.errorMessage,
                tagsError = tagsResult.errorMessage,
            )
            showDialog = false
        } else {
            val listTags = mutableListOf<Tags>()
            tagChip.forEach{ data ->
                listTags.add(Tags(name = data.text))
            }

            addActivity(
                description = stateAddActivity.activity,
                dogId = stateAddActivity.dogId,
                tags = listTags
            )
            showDialog = true
        }
    }
}
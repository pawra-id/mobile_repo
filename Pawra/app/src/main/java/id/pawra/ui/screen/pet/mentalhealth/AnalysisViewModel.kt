package id.pawra.ui.screen.pet.mentalhealth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.pawra.data.remote.response.AnalysisResponseItem
import id.pawra.data.remote.response.ShareAnalysisResponse
import id.pawra.data.repository.AnalysisRepository
import id.pawra.data.repository.AuthRepository
import id.pawra.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AnalysisViewModel(
    private val analysisRepository: AnalysisRepository,
    private val authRepository: AuthRepository,
): ViewModel() {

    private val _analysisState: MutableStateFlow<UiState<List<AnalysisResponseItem>>> = MutableStateFlow(
        UiState.Loading)
    val analysisState: StateFlow<UiState<List<AnalysisResponseItem>>>
        get() = _analysisState

    private val _analysisDetailState: MutableStateFlow<UiState<AnalysisResponseItem>> = MutableStateFlow(
        UiState.Loading)
    val analysisDetailState: StateFlow<UiState<AnalysisResponseItem>>
        get() = _analysisDetailState

    private val _addAnalysisState: MutableStateFlow<UiState<AnalysisResponseItem>> = MutableStateFlow(
        UiState.None)
    val addAnalysisState: StateFlow<UiState<AnalysisResponseItem>>
        get() = _addAnalysisState

    private val _sharedAnalysisState: MutableStateFlow<UiState<List<AnalysisResponseItem>>> = MutableStateFlow(
        UiState.Loading
    )
    val sharedAnalysisState: StateFlow<UiState<List<AnalysisResponseItem>>>
        get() = _sharedAnalysisState

    private val _shareState: MutableStateFlow<ShareAnalysisResponse> = MutableStateFlow(
        ShareAnalysisResponse(
            error = false,
            message = ""
        )
    )
    val shareState: StateFlow<ShareAnalysisResponse>
        get() = _shareState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getSpecificAnalysis(petId: Int, keyword: String) {
        _query.value = keyword
        viewModelScope.launch {
            val user = authRepository.getSession().first()
            analysisRepository.getSpecificAnalysis(user, petId, keyword)
                .collect { analysis ->
                    when {
                        analysis.error != null ->{
                            _analysisState.value = UiState.Error(analysis.error)
                        }
                        else -> {
                            _analysisState.value = UiState.Success(analysis.items?.sortedByDescending { it.createdAt } ?: listOf())
                        }
                    }
                }


        }
    }

    fun addAnalysis(petId: Int, days: Int) {
        viewModelScope.launch {
            _addAnalysisState.value = UiState.Loading
            val user = authRepository.getSession().first()
            analysisRepository.addAnalysis(user, petId, days)
                .collect { addAnalysis ->
                    when {
                        addAnalysis.error != null ->{
                            _addAnalysisState.value = UiState.Error(addAnalysis.error)
                        }
                        else -> {
                            _addAnalysisState.value = UiState.Success(addAnalysis)
                        }
                    }
                }
        }
    }

    fun getDetailAnalysis(analysisId: Int) {
        viewModelScope.launch {
            val user = authRepository.getSession().first()
            analysisRepository.getDetailAnalysis(user, analysisId)
                .collect { analysisDetail ->
                    when {
                        analysisDetail.error != null ->{
                            _analysisDetailState.value = UiState.Error(analysisDetail.error)
                        }
                        else -> {
                            _analysisDetailState.value = UiState.Success(analysisDetail)
                        }
                    }
                }
        }
    }

    fun shareAnalysis(analysisId: Int) {
        viewModelScope.launch {
            val user = authRepository.getSession().first()
            analysisRepository.shareAnalysis(user, analysisId)
                .collect { analysisShare ->
                    _shareState.value = analysisShare
                }
        }
    }

    fun unshareAnalysis(analysisId: Int) {
        viewModelScope.launch {
            val user = authRepository.getSession().first()
            analysisRepository.unshareAnalysis(user, analysisId)
                .collect { analysisShare ->
                    _shareState.value = analysisShare
                }
        }
    }

    fun getSharedAnalysis(keyword: String) {
        _query.value = keyword
        viewModelScope.launch {
            val user = authRepository.getSession().first()
            analysisRepository.getSharedAnalysis(user, keyword)
                .collect { sharedAnalysis ->
                    when {
                        sharedAnalysis.error != null ->{
                            _sharedAnalysisState.value = UiState.Error(sharedAnalysis.error)
                        }
                        else -> {
                            _sharedAnalysisState.value = UiState.Success(sharedAnalysis.items?.sortedByDescending { it.createdAt } ?: listOf())
                        }
                    }
                }
        }
    }
}
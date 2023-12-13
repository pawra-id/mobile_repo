package id.pawra.ui.screen.pet.mentalhealth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.pawra.data.remote.response.AnalysisResponseItem
import id.pawra.data.remote.response.ShareAnalysisResponse
import id.pawra.data.remote.response.VetResponseItem
import id.pawra.data.repository.AnalysisRepository
import id.pawra.data.repository.AuthRepository
import id.pawra.data.repository.VetRepository
import id.pawra.ui.common.UiState
import id.pawra.ui.components.vets.FilterVets
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
                            _analysisState.value = UiState.Success(analysis.items ?: listOf())
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
}
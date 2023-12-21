package id.pawra.ui.screen.explore

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.pawra.data.remote.response.BlogsResponseItem
import id.pawra.data.repository.AuthRepository
import id.pawra.data.repository.BlogsRepository
import id.pawra.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BlogsViewModel (
    private val blogsRepository: BlogsRepository,
    private val authRepository: AuthRepository,
): ViewModel() {

    private val _blogsState: MutableStateFlow<UiState<List<BlogsResponseItem>>> = MutableStateFlow(
        UiState.Loading)
    val blogsState: StateFlow<UiState<List<BlogsResponseItem>>>
        get() = _blogsState

    private val _blogsDetailState: MutableStateFlow<UiState<BlogsResponseItem>> = MutableStateFlow(
        UiState.Loading)
    val blogsDetailState: StateFlow<UiState<BlogsResponseItem>>
        get() = _blogsDetailState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getBlogs(keyword: String) {
        _query.value = keyword
        viewModelScope.launch {
            val user = authRepository.getSession().first()
            blogsRepository.getBlogs(user, keyword)
                .collect { blogs ->
                    when {
                        blogs.error != null ->{
                            _blogsState.value = UiState.Error(blogs.error)
                        }
                        else -> {
                            _blogsState.value = UiState.Success(blogs.items ?: listOf())
                        }
                    }
                }


        }
    }

    fun getDetailBlogs(blogsId: Int) {
        viewModelScope.launch {
            val user = authRepository.getSession().first()
            blogsRepository.getDetailBlogs(user, blogsId)
                .collect { blogsDetail ->
                    when {
                        blogsDetail.error != null ->{
                            _blogsDetailState.value = UiState.Error(blogsDetail.error)
                        }
                        else -> {
                            _blogsDetailState.value = UiState.Success(blogsDetail)
                        }
                    }
                }
        }
    }
}
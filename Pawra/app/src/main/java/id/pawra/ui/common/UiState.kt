package id.pawra.ui.common

sealed class UiState<out T: Any?> {
    data object None : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
    data class Success<out T: Any>(val data: T) : UiState<T>()
    data class Error(val errorMessage: String) : UiState<Nothing>()
}
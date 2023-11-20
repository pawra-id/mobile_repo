package id.pawra.ui.screen.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.pawra.data.auth.AuthRepository
import id.pawra.data.local.preference.SessionModel
import id.pawra.data.remote.response.SignInResponse
import id.pawra.data.remote.response.SignUpResponse
import id.pawra.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
): ViewModel() {

    private val _signInState: MutableStateFlow<UiState<SignInResponse>> = MutableStateFlow(UiState.None)
    val signInState: StateFlow<UiState<SignInResponse>>
        get() = _signInState

    private val _signUpState: MutableStateFlow<UiState<SignUpResponse>> = MutableStateFlow(UiState.None)
    val signUpState: StateFlow<UiState<SignUpResponse>>
        get() = _signUpState

    private val _sessionState: MutableStateFlow<UiState<SessionModel>> = MutableStateFlow(UiState.None)
    val sessionState: StateFlow<UiState<SessionModel>>
        get() = _sessionState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            authRepository.signIn(email, password)
                .catch {
                    _signInState.value = UiState.Error(it.message.toString())
                }
                .collect { users ->
                    _signInState.value = users
                }
        }
    }

    fun signUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            authRepository.signUp(name, email, password)
                .catch {
                    _signUpState.value = UiState.Error(it.message.toString())
                }
                .collect { users ->
                    _signUpState.value = users
                }
        }
    }

    fun getSession() {
        viewModelScope.launch {
            try {
                val sessionModel = authRepository.getSession().first()
                Log.d("AuthViewModel", "Session Model: $sessionModel")
                _sessionState.value = UiState.Success(sessionModel)
            } catch (e: Exception) {
                _sessionState.value = UiState.Error(e.message.toString())
            }
        }
    }
}
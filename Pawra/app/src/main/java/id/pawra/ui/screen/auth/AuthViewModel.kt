package id.pawra.ui.screen.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.pawra.data.auth.AuthRepository
import id.pawra.data.local.preference.SessionModel
import id.pawra.data.remote.response.SignInResponse
import id.pawra.data.remote.response.SignUpResponse
import id.pawra.ui.common.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val validateName: ValidateName = ValidateName(),
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validateRepeatedPassword: ValidateRepeatedPassword = ValidateRepeatedPassword(),
    private val validateTerms: ValidateTerms = ValidateTerms()
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

    private fun signIn(email: String, password: String) {
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

    private fun signUp(name: String, email: String, password: String) {
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

    fun logout() {
        viewModelScope.launch {
            try {
                authRepository.logout()
                _sessionState.value = UiState.Success(SessionModel("", false, "", "", ""))
            } catch (e: Exception) {
                _sessionState.value = UiState.Error(e.message.toString())
            }
        }
    }

    var stateSignIn by mutableStateOf(SignInFormState())
    var stateSignUp by mutableStateOf(SignUpFormState())

    var showDialog by mutableStateOf(false)

    fun onEventSignUp(event: SignUpFormEvent) {
        when(event) {
            is SignUpFormEvent.NameChanged -> {
                stateSignUp = stateSignUp.copy(name = event.name)
                val nameResult = validateName.execute(stateSignUp.name)
                stateSignUp = stateSignUp.copy(
                    nameError = nameResult.errorMessage
                )
            }
            is SignUpFormEvent.EmailChanged -> {
                stateSignUp = stateSignUp.copy(email = event.email)
                val nameResult = validateEmail.execute(stateSignUp.email)
                stateSignUp = stateSignUp.copy(
                    emailError = nameResult.errorMessage
                )
            }
            is SignUpFormEvent.PasswordChanged -> {
                stateSignUp = stateSignUp.copy(password = event.password)
                val passwordResult = validatePassword.execute(stateSignUp.password)
                stateSignUp = stateSignUp.copy(
                    passwordError = passwordResult.errorMessage
                )
            }
            is SignUpFormEvent.RepeatedPasswordChanged -> {
                stateSignUp = stateSignUp.copy(repeatedPassword = event.repeatedPassword)
                val repeatedPasswordResult = validateRepeatedPassword.execute(stateSignUp.password, stateSignUp.repeatedPassword)
                stateSignUp = stateSignUp.copy(
                    repeatedPasswordError = repeatedPasswordResult.errorMessage
                )
            }
            is SignUpFormEvent.AcceptTerms -> {
                stateSignUp = stateSignUp.copy(acceptedTerms = event.isAccepted)
                val termResult = validateTerms.execute(stateSignUp.acceptedTerms)
                stateSignUp = stateSignUp.copy(
                    termsError = termResult.errorMessage
                )
            }
            is SignUpFormEvent.Submit -> {
                submitDataSignUp()
            }
        }
    }

    fun onEventSignIn(event: SignInFormEvent) {
        when(event) {
            is SignInFormEvent.EmailChanged -> {
                stateSignIn = stateSignIn.copy(
                    email = event.email
                )
                val emailResult = validateEmail.execute(stateSignIn.email)
                stateSignIn = stateSignIn.copy(
                    emailError = emailResult.errorMessage
                )
            }
            is SignInFormEvent.PasswordChanged -> {
                stateSignIn = stateSignIn.copy(
                    password = event.password
                )
                val passwordResult = validatePassword.execute(stateSignIn.password)
                stateSignIn = stateSignIn.copy(
                    passwordError = passwordResult.errorMessage
                )
            }
            is SignInFormEvent.Submit -> {
                submitDataSignIn()
            }
        }
    }

    private fun submitDataSignUp() {
        val nameResult = validateName.execute(stateSignUp.name)
        val emailResult = validateEmail.execute(stateSignUp.email)
        val passwordResult = validatePassword.execute(stateSignUp.password)
        val repeatedPasswordResult = validateRepeatedPassword.execute(
            stateSignUp.password, stateSignUp.repeatedPassword
        )
        val termsResult = validateTerms.execute(stateSignUp.acceptedTerms)

        val hasError = listOf(
            nameResult,
            emailResult,
            passwordResult,
            repeatedPasswordResult,
            termsResult
        ).any { !it.successful }

        if(hasError) {
            stateSignUp = stateSignUp.copy(
                nameError = nameResult.errorMessage,
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatedPasswordResult.errorMessage,
                termsError = termsResult.errorMessage
            )
            showDialog = false
        } else {
            signUp(
                stateSignUp.name,
                stateSignUp.email,
                stateSignUp.password
            )
            showDialog = true
        }
    }

    private fun submitDataSignIn() {
        val emailResult = validateEmail.execute(stateSignIn.email)
        val passwordResult = validatePassword.execute(stateSignIn.password)

        val hasError = listOf(
            emailResult,
            passwordResult,
        ).any { !it.successful }

        if(hasError) {
            stateSignIn = stateSignIn.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
            )
            showDialog = false
        } else {
            signIn(
                stateSignIn.email,
                stateSignIn.password
            )
            showDialog = true
        }
    }
}
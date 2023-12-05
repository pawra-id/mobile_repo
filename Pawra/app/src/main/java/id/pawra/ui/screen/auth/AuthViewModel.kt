package id.pawra.ui.screen.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.pawra.data.repository.AuthRepository
import id.pawra.data.local.preference.SessionModel
import id.pawra.data.remote.response.SignInResponse
import id.pawra.data.remote.response.SignUpResponse
import id.pawra.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

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

    private val _updateProfileState: MutableStateFlow<UiState<SignUpResponse>> = MutableStateFlow(UiState.None)
    val updateProfileState: StateFlow<UiState<SignUpResponse>>
        get() = _updateProfileState

    private val _sessionState: MutableStateFlow<SessionModel> = MutableStateFlow(SessionModel(0, "", false, "", "", "", ""))
    val sessionState: StateFlow<SessionModel>
        get() = _sessionState

    private fun signIn(username: String, password: String) {
        viewModelScope.launch {
            _signInState.value = UiState.Loading
            authRepository.signIn(username, password)
                .collect { user ->
                    when {
                        user.error != null -> _signInState.value = UiState.Error(user.error)
                        else -> _signInState.value = UiState.Success(user)
                    }
                }
        }
    }

    private fun signUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            _signUpState.value = UiState.Loading
            authRepository.signUp(name, email, password)
                .collect { user ->
                    when {
                        user.error != null -> _signUpState.value = UiState.Error(user.error)
                        else -> _signUpState.value = UiState.Success(user)
                    }
                }
        }
    }

    fun updateProfile(
        id: Int,
        token: String,
        name: String,
        email: String,
        summary: String,
        imageUrl: String,
        file: MultipartBody.Part? = null
    ) {
        viewModelScope.launch {
            val user = authRepository.getSession().first()
            var image = imageUrl
            if (file != null) {
                authRepository.postProfileImage(user, file)
                    .collect { result ->
                        image = result
                    }
            }

            authRepository.updateProfile(
                id = id,
                token = token,
                username = name,
                email = email,
                summary = summary,
                image = image
            ).collect { userDetail ->
                when {
                    userDetail.error != null -> _signUpState.value = UiState.Error(userDetail.error)
                    else -> _signUpState.value = UiState.Success(userDetail)
                }
            }
        }
    }

    fun getSession() {
        viewModelScope.launch {
            _sessionState.value = authRepository.getSession().first()
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _sessionState.value = SessionModel(0, "", false, "", "", "", "")
        }
    }

    var stateSignIn by mutableStateOf(SignInFormState())
    var stateSignUp by mutableStateOf(SignUpFormState())

    var stateUpdateProfile by mutableStateOf(UpdateProfileFormState())

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
            is SignInFormEvent.NameChanged -> {
                stateSignIn = stateSignIn.copy(
                    name = event.name
                )
                val nameResult = validateName.execute(stateSignIn.name)
                stateSignIn = stateSignIn.copy(
                    nameError = nameResult.errorMessage
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

    // TODO: validation form
    fun onEventUpdateProfile(event: UpdateProfileFormEvent) {
        when(event) {
            is UpdateProfileFormEvent.NameChanged -> {
                stateUpdateProfile = stateUpdateProfile.copy(name = event.name)
                val nameResult = validateName.execute(stateUpdateProfile.name)
                stateUpdateProfile = stateUpdateProfile.copy(
                    nameError = nameResult.errorMessage
                )
            }
            is UpdateProfileFormEvent.EmailChanged -> {
                stateUpdateProfile = stateUpdateProfile.copy(email = event.email)
                val nameResult = validateEmail.execute(stateUpdateProfile.email)
                stateUpdateProfile = stateUpdateProfile.copy(
                    emailError = nameResult.errorMessage
                )
            }
            is UpdateProfileFormEvent.Update -> {
                updateDataProfile()
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
        val nameResult = validateName.execute(stateSignIn.name)
        val passwordResult = validatePassword.execute(stateSignIn.password)

        val hasError = listOf(
            nameResult,
            passwordResult,
        ).any { !it.successful }

        if(hasError) {
            stateSignIn = stateSignIn.copy(
                nameError = nameResult.errorMessage,
                passwordError = passwordResult.errorMessage,
            )
            showDialog = false
        } else {
            signIn(
                stateSignIn.name,
                stateSignIn.password
            )
            showDialog = true
        }
    }

    private fun updateDataProfile() {
        val nameResult = validateName.execute(stateUpdateProfile.name)
        val emailResult = validateEmail.execute(stateUpdateProfile.email)

        val hasError = listOf(
            nameResult,
            emailResult,
        ).any { !it.successful }

        if(hasError) {
            stateUpdateProfile = stateUpdateProfile.copy(
                nameError = nameResult.errorMessage,
                emailError = emailResult.errorMessage,
            )
            showDialog = false
        } else {
            showDialog = true
        }
    }
}
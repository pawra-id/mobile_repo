package id.pawra.ui.screen.auth

sealed class SignUpFormEvent {
    data class NameChanged(val name: String) : SignUpFormEvent()
    data class EmailChanged(val email: String) : SignUpFormEvent()
    data class PasswordChanged(val password: String) : SignUpFormEvent()
    data class RepeatedPasswordChanged(
        val repeatedPassword: String
    ) : SignUpFormEvent()
    data class AcceptTerms(val isAccepted: Boolean) : SignUpFormEvent()

    data object Submit: SignUpFormEvent()
}

sealed class SignInFormEvent {
    data class NameChanged(val name: String) : SignInFormEvent()
    data class PasswordChanged(val password: String) : SignInFormEvent()

    data object Submit: SignInFormEvent()
}
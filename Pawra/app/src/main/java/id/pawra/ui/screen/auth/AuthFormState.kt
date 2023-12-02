package id.pawra.ui.screen.auth

data class SignUpFormState(
    val name: String = "",
    val nameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordError: String? = null,
    val acceptedTerms: Boolean = false,
    val termsError: String? = null
)

data class SignInFormState(
    val name: String = "",
    val nameError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
)
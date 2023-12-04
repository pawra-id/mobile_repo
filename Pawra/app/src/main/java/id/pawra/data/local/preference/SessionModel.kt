package id.pawra.data.local.preference

data class SessionModel(
    val id: Int,
    val token: String,
    val isLogin: Boolean = false,
    val name: String,
    val email: String,
    val summary: String,
    val image: String,
)

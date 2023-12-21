package id.pawra.data.local.preference

data class SessionModel(
    val id: Int,
    val token: String,
    val name: String,
    val email: String,
    val summary: String,
    val image: String,
    val latitude: String,
    val longitude: String,
    val address: String,
    val expire: String,
    val isLogin: Boolean = false,
    val isLaunched: Boolean = false
)
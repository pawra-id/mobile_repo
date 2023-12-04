package id.pawra.data.remote.response

import com.google.gson.annotations.SerializedName

data class SignInResponse(

    @field:SerializedName("user")
    val user: User? = null,

    @field:SerializedName("access_token")
    val accessToken: String? = null,

    @field:SerializedName("token_type")
    val tokenType: String? = null,

    val error: String? = null
)

data class User(

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("summary")
    val summary: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null
)

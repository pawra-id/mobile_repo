package id.pawra.data.remote.response

import com.google.gson.annotations.SerializedName

data class SignInResponse(

    @field:SerializedName("loginResult")
    val loginResult: SignInResult? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class SignInResult(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("token")
    val token: String? = null
)

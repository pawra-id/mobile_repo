package id.pawra.data.remote.retrofit

import id.pawra.data.remote.response.PetResponse
import id.pawra.data.remote.response.PetResponseItem
import id.pawra.data.remote.response.SignInResponse
import id.pawra.data.remote.response.SignUpResponse
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("users")
    suspend fun signUp(
        @Body body: MutableMap<String, String>
    ): SignUpResponse

    @FormUrlEncoded
    @POST("token")
    suspend fun signIn(
        @Field("username") username: String,
        @Field("password") password: String
    ): SignInResponse

    @GET("dogs")
    suspend fun getDogs(@Header("Authorization") authHeader: String): List<PetResponseItem>

    @GET("dogs/{id}")
    suspend fun getDetailDog(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Int
    ): PetResponseItem
}
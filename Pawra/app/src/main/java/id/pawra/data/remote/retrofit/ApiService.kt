package id.pawra.data.remote.retrofit

import id.pawra.data.remote.response.ActivitiesResponseItem
import id.pawra.data.remote.response.PetResponse
import id.pawra.data.remote.response.PetResponseItem
import id.pawra.data.remote.response.SignInResponse
import id.pawra.data.remote.response.SignUpResponse
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @POST("users/")
    suspend fun signUp(
        @Body body: MutableMap<String, Any>
    ): SignUpResponse

    @FormUrlEncoded
    @POST("token")
    suspend fun signIn(
        @Field("username") username: String,
        @Field("password") password: String
    ): SignInResponse

    @PUT("users/{id}")
    suspend fun updateProfile(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Int,
        @Body body: MutableMap<String, Any>
    ): SignUpResponse

    @Multipart
    @POST("users/image/")
    suspend fun postProfileImage(
        @Header("Authorization") authHeader: String,
        @Part file: MultipartBody.Part
    ): String

    @GET("dogs/")
    suspend fun getDogs(@Header("Authorization") authHeader: String): List<PetResponseItem>

    @GET("dogs/{id}")
    suspend fun getDetailDog(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Int
    ): PetResponseItem

    @POST("dogs/")
    suspend fun addDog(
        @Header("Authorization") authHeader: String,
        @Body body: MutableMap<String, Any>
    ): PetResponseItem

    @Multipart
    @POST("dogs/image/")
    suspend fun postDogImage(
        @Header("Authorization") authHeader: String,
        @Part file: MultipartBody.Part
    ): String

    @GET("activities/")
    suspend fun getActivities(@Header("Authorization") authHeader: String): List<ActivitiesResponseItem>
}
package id.pawra.data.remote.retrofit

import id.pawra.data.remote.response.ActivitiesResponseItem
import id.pawra.data.remote.response.PetResponseItem
import id.pawra.data.remote.response.SignInResponse
import id.pawra.data.remote.response.SignUpResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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
    @POST("users/image")
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

    @DELETE("dogs/{id}")
    suspend fun deleteDog(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Int
    ): PetResponseItem

    @PUT("dogs/{id}")
    suspend fun updateDog(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Int,
        @Body body: MutableMap<String, Any>
    ): PetResponseItem

    @POST("dogs/")
    suspend fun addDog(
        @Header("Authorization") authHeader: String,
        @Body body: MutableMap<String, Any>
    ): PetResponseItem

    @Multipart
    @POST("dogs/image")
    suspend fun postDogImage(
        @Header("Authorization") authHeader: String,
        @Part file: MultipartBody.Part
    ): String

    @GET("activities/")
    suspend fun getActivities(
        @Header("Authorization") authHeader: String,
        @Query("search") keyword: String? = "",
        @Query("size") size: Int? = 15,
        @Query("page") skip: Int? = 0
    ): List<ActivitiesResponseItem>

    @GET("activities/dog/{id}")
    suspend fun getSpesificActivities(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Int,
        @Query("search") keyword: String? = "",
        @Query("size") size: Int? = 15,
        @Query("page") skip: Int? = 0
    ): List<ActivitiesResponseItem>

    @GET("activities/{id}")
    suspend fun getDetailActivitiy(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Int
    ): ActivitiesResponseItem

    @POST("activities/")
    suspend fun addActivity(
        @Header("Authorization") authHeader: String,
        @Body body: MutableMap<String, Any>
    ): ActivitiesResponseItem
}
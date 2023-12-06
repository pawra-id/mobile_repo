package id.pawra.data.repository

import id.pawra.data.local.preference.PetData
import id.pawra.data.local.preference.SessionModel
import id.pawra.data.remote.response.PetResponse
import id.pawra.data.remote.response.PetResponseItem
import id.pawra.data.remote.response.SignInResponse
import id.pawra.data.remote.response.SignUpResponse
import id.pawra.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.MultipartBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException

class PetRepository private constructor(
    private val apiService: ApiService,
) {
    suspend fun getDog(user: SessionModel): Flow<PetResponse> {
        try {
            val response = apiService.getDogs("Bearer ${user.token}")
            return flowOf(
                PetResponse(
                    petResponse = response
                )
            )
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                val jsonError = errorBody?.let { JSONObject(it) }
                jsonError?.optString("detail") ?: "Unknown error"
            } catch (jsonException: JSONException) {
                "Error parsing JSON"
            }
            return flowOf(
                PetResponse(
                    error = errorMessage
                )
            )

        } catch (e: Exception) {
            return flowOf(
                PetResponse(
                    error = e.message
                )
            )
        }
    }

    suspend fun getDetailDog(user: SessionModel, petId: Int): Flow<PetResponseItem> {
        try {
            return flowOf(apiService.getDetailDog("Bearer ${user.token}", petId))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                val jsonError = errorBody?.let { JSONObject(it) }
                jsonError?.optString("detail") ?: "Unknown error"
            } catch (jsonException: JSONException) {
                "Error parsing JSON"
            }
            return flowOf(
                PetResponseItem(
                    id = 0,
                    error = errorMessage
                )
            )

        } catch (e: Exception) {
            return flowOf(
                PetResponseItem(
                    id = 0,
                    error = e.message
                )
            )
        }
    }

    suspend fun addDog(user: SessionModel, petData: PetData): Flow<PetResponseItem> {
        try {
            val userData = mutableMapOf<String, Any>()
            userData["name"] = petData.name
            userData["gender"] = petData.gender
            userData["age"] = petData.age
            userData["breed"] = petData.breed
            userData["neutered"] = petData.neutred
            userData["color"] = petData.primaryColor
            userData["weight"] = petData.weight
            userData["height"] = petData.height
            userData["description"] = petData.summary.toString()
            userData["image"] = petData.image

            return flowOf(apiService.addDog("Bearer ${user.token}", userData))

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                val jsonError = errorBody?.let { JSONObject(it) }
                jsonError?.optString("detail") ?: "Unknown error"
            } catch (jsonException: JSONException) {
                "Error parsing JSON"
            }
            return flowOf(
                PetResponseItem(
                    id = 0,
                    error = errorMessage
                )
            )

        } catch (e: Exception) {
            return flowOf(
                PetResponseItem(
                    id = 0,
                    error = e.message
                )
            )
        }
    }

    suspend fun postDogImage(user: SessionModel, file: MultipartBody.Part): Flow<String> {
        try {
            return flowOf(apiService.postDogImage("Bearer ${user.token}", file))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                val jsonError = errorBody?.let { JSONObject(it) }
                jsonError?.optString("detail") ?: "Unknown error"
            } catch (jsonException: JSONException) {
                "Error parsing JSON"
            }
            return flowOf(
                errorMessage
            )
        } catch (e: Exception) {
            return flowOf(
                e.message ?: ""
            )
        }
    }

    companion object {
        @Volatile
        private var instance: PetRepository? = null
        fun getInstance(
            apiService: ApiService
        ): PetRepository =
            instance ?: synchronized(this) {
                instance ?: PetRepository(apiService)
            }.also { instance = it }
    }
}
package id.pawra.data.repository

import id.pawra.data.local.preference.SessionModel
import id.pawra.data.remote.response.VetResponse
import id.pawra.data.remote.response.VetResponseItem
import id.pawra.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException

class VetRepository private constructor(
    private val apiService: ApiService,
) {
    suspend fun getVets(user: SessionModel, keyword: String? = ""): Flow<VetResponse> {
        try {
            val response = apiService.getVets("Bearer ${user.token}", keyword)
            return flowOf(response)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                val jsonError = errorBody?.let { JSONObject(it) }
                jsonError?.optString("detail") ?: "Unknown error"
            } catch (jsonException: JSONException) {
                "Error parsing JSON"
            }
            return flowOf(
                VetResponse(
                    error = errorMessage
                )
            )

        } catch (e: Exception) {
            return flowOf(
                VetResponse(
                    error = e.message
                )
            )
        }
    }

    suspend fun getDetailVet(user: SessionModel, activityId: Int): Flow<VetResponseItem> {
        try {
            return flowOf(
                apiService.getDetailVet("Bearer ${user.token}", activityId)
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
                VetResponseItem(
                    id = 0,
                    error = errorMessage
                )
            )

        } catch (e: Exception) {
            return flowOf(
                VetResponseItem(
                    id = 0,
                    error = e.message
                )
            )
        }
    }

    companion object {
        @Volatile
        private var instance: VetRepository? = null
        fun getInstance(
            apiService: ApiService
        ): VetRepository =
            instance ?: synchronized(this) {
                instance ?: VetRepository(apiService)
            }.also { instance = it }
    }
}
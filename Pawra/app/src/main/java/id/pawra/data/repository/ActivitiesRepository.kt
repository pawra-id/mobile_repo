package id.pawra.data.repository

import id.pawra.data.local.preference.SessionModel
import id.pawra.data.remote.response.ActivitiesResponse
import id.pawra.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException

class ActivitiesRepository private constructor(
    private val apiService: ApiService,
) {
    suspend fun getActivities(user: SessionModel): Flow<ActivitiesResponse> {
        try {
            val response = apiService.getActivities("Bearer ${user.token}")
            return flowOf(
                ActivitiesResponse(
                    activitiesResponse  = response
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
                ActivitiesResponse(
                    error = errorMessage
                )
            )

        } catch (e: Exception) {
            return flowOf(
                ActivitiesResponse(
                    error = e.message
                )
            )
        }
    }

    companion object {
        @Volatile
        private var instance: ActivitiesRepository? = null
        fun getInstance(
            apiService: ApiService
        ): ActivitiesRepository =
            instance ?: synchronized(this) {
                instance ?: ActivitiesRepository(apiService)
            }.also { instance = it }
    }
}
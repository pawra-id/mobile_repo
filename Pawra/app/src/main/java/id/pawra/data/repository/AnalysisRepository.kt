package id.pawra.data.repository

import id.pawra.data.local.preference.SessionModel
import id.pawra.data.remote.response.AnalysisResponse
import id.pawra.data.remote.response.AnalysisResponseItem
import id.pawra.data.remote.response.ShareAnalysisResponse
import id.pawra.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException

class AnalysisRepository private constructor(
    private val apiService: ApiService,
) {
    suspend fun getSpecificAnalysis(user: SessionModel, petId: Int, keyword: String): Flow<AnalysisResponse> {
        try {
            val response = apiService.getSpecificAnalysis("Bearer ${user.token}", petId, keyword)
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
                AnalysisResponse(
                    error = errorMessage
                )
            )

        } catch (e: Exception) {
            return flowOf(
                AnalysisResponse(
                    error = e.message
                )
            )
        }
    }

    suspend fun getSharedAnalysis(user: SessionModel, keyword: String): Flow<AnalysisResponse> {
        try {
            val response = apiService.getSharedAnalysis("Bearer ${user.token}", keyword)
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
                AnalysisResponse(
                    error = errorMessage
                )
            )

        } catch (e: Exception) {
            return flowOf(
                AnalysisResponse(
                    error = e.message
                )
            )
        }
    }

    suspend fun getDetailAnalysis(user: SessionModel, analysisId: Int): Flow<AnalysisResponseItem> {
        try {
            return flowOf(
                apiService.getDetailAnalysis("Bearer ${user.token}", analysisId)
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
                AnalysisResponseItem(
                    id = 0,
                    error = errorMessage
                )
            )

        } catch (e: Exception) {
            return flowOf(
                AnalysisResponseItem(
                    id = 0,
                    error = e.message
                )
            )
        }
    }

    suspend fun getDetailSharedAnalysis(user: SessionModel, analysisId: Int): Flow<AnalysisResponseItem> {
        try {
            return flowOf(
                apiService.getDetailSharedAnalysis("Bearer ${user.token}", analysisId)
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
                AnalysisResponseItem(
                    id = 0,
                    error = errorMessage
                )
            )

        } catch (e: Exception) {
            return flowOf(
                AnalysisResponseItem(
                    id = 0,
                    error = e.message
                )
            )
        }
    }

    suspend fun addAnalysis(user: SessionModel, petId: Int, days: Int): Flow<AnalysisResponseItem> {
        try {
            val data = mutableMapOf<String, Any>()
            data["dog_id"] = petId
            return flowOf(apiService.addAnalysis("Bearer ${user.token}", days, data))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                val jsonError = errorBody?.let { JSONObject(it) }
                jsonError?.optString("detail") ?: "Unknown error"
            } catch (jsonException: JSONException) {
                "Error parsing JSON"
            }
            return flowOf(
                AnalysisResponseItem(
                    id = 0,
                    error = errorMessage
                )
            )

        } catch (e: Exception) {
            return flowOf(
                AnalysisResponseItem(
                    id = 0,
                    error = e.message
                )
            )
        }
    }

    suspend fun shareAnalysis(user: SessionModel, analysisId: Int): Flow<ShareAnalysisResponse> {
        return try {
            flowOf(
                ShareAnalysisResponse(
                    message = apiService.shareAnalysis("Bearer ${user.token}", analysisId).message,
                    error = false
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
            flowOf(
                ShareAnalysisResponse(
                    message = errorMessage,
                    error = true
                )
            )

        } catch (e: Exception) {
            flowOf(
                ShareAnalysisResponse(
                    message = e.message.toString(),
                    error = true
                )
            )
        }
    }

    suspend fun unshareAnalysis(user: SessionModel, analysisId: Int): Flow<ShareAnalysisResponse> {
        return try {
            flowOf(
                ShareAnalysisResponse(
                    message = apiService.unshareAnalysis("Bearer ${user.token}", analysisId).message,
                    error = false
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
            flowOf(
                ShareAnalysisResponse(
                    message = errorMessage,
                    error = true
                )
            )

        } catch (e: Exception) {
            flowOf(
                ShareAnalysisResponse(
                    message = e.message.toString(),
                    error = true
                )
            )
        }
    }

    companion object {
        @Volatile
        private var instance: AnalysisRepository? = null
        fun getInstance(
            apiService: ApiService
        ): AnalysisRepository =
            instance ?: synchronized(this) {
                instance ?: AnalysisRepository(apiService)
            }.also { instance = it }
    }
}
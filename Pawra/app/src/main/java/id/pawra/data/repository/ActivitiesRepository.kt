package id.pawra.data.repository

import id.pawra.data.local.preference.ActivityData
import id.pawra.data.local.preference.SessionModel
import id.pawra.data.remote.response.ActivitiesResponse
import id.pawra.data.remote.response.ActivitiesResponseItem
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

    suspend fun getSpecificActivities(user: SessionModel, petId: Int, keyword: String? = ""): Flow<ActivitiesResponse> {
        try {
            val response = apiService.getSpecificActivities("Bearer ${user.token}", petId, keyword)
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

    suspend fun getDetailActivity(user: SessionModel, activityId: Int): Flow<ActivitiesResponseItem> {
        try {
            return flowOf(
                apiService.getDetailActivity("Bearer ${user.token}", activityId)
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
                ActivitiesResponseItem(
                    id = 0,
                    error = errorMessage
                )
            )

        } catch (e: Exception) {
            return flowOf(
                ActivitiesResponseItem(
                    id = 0,
                    error = e.message
                )
            )
        }
    }

    suspend fun addActivity(user: SessionModel, activityData: ActivityData): Flow<ActivitiesResponseItem> {
        fun removeEmptyMutableMap(data: MutableList<MutableMap<String, String>>): MutableList<MutableMap<String, String>> {
            return data.filter { it.isNotEmpty() }.toMutableList()
        }

        try {
            val listTags = mutableListOf(mutableMapOf<String, String>())
            activityData.tags?.forEach { tag ->
                listTags.add(mapOf(Pair("name", tag.name)) as MutableMap<String, String>)
            }
            val filteredData = removeEmptyMutableMap(listTags)
            val data = mutableMapOf<String, Any>()
            data["description"] = activityData.description
            data["dog_id"] = activityData.dogId
            data["tags"] = filteredData

            return flowOf(apiService.addActivity("Bearer ${user.token}", data))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                val jsonError = errorBody?.let { JSONObject(it) }
                jsonError?.optString("detail") ?: "Unknown error"
            } catch (jsonException: JSONException) {
                "Error parsing JSON"
            }
            return flowOf(
                ActivitiesResponseItem(
                    id = 0,
                    error = errorMessage
                )
            )

        } catch (e: Exception) {
            return flowOf(
                ActivitiesResponseItem(
                    id = 0,
                    error = e.message
                )
            )
        }
    }

    suspend fun updateActivity(user: SessionModel, activityId: Int, activityData: ActivityData): Flow<ActivitiesResponseItem> {
        fun removeEmptyMutableMap(data: MutableList<MutableMap<String, String>>): MutableList<MutableMap<String, String>> {
            return data.filter { it.isNotEmpty() }.toMutableList()
        }

        try {
            val listTags = mutableListOf(mutableMapOf<String, String>())
            activityData.tags?.forEach { tag ->
                listTags.add(mapOf(Pair("name", tag.name)) as MutableMap<String, String>)
            }
            val filteredData = removeEmptyMutableMap(listTags)
            val data = mutableMapOf<String, Any>()
            data["description"] = activityData.description
            data["dog_id"] = activityData.dogId
            data["tags"] = filteredData

            return flowOf(apiService.updateActivity("Bearer ${user.token}", activityId, data))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                val jsonError = errorBody?.let { JSONObject(it) }
                jsonError?.optString("detail") ?: "Unknown error"
            } catch (jsonException: JSONException) {
                "Error parsing JSON"
            }
            return flowOf(
                ActivitiesResponseItem(
                    id = 0,
                    error = errorMessage
                )
            )

        } catch (e: Exception) {
            return flowOf(
                ActivitiesResponseItem(
                    id = 0,
                    error = e.message
                )
            )
        }
    }

    suspend fun deleteActivity(user: SessionModel, activityId: Int): Flow<String> {
        try {
            return flowOf(apiService.deleteActivity("Bearer ${user.token}", activityId))

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
        private var instance: ActivitiesRepository? = null
        fun getInstance(
            apiService: ApiService
        ): ActivitiesRepository =
            instance ?: synchronized(this) {
                instance ?: ActivitiesRepository(apiService)
            }.also { instance = it }
    }
}
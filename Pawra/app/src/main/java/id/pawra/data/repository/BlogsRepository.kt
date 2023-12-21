package id.pawra.data.repository

import id.pawra.data.local.preference.SessionModel
import id.pawra.data.remote.response.BlogsResponse
import id.pawra.data.remote.response.BlogsResponseItem
import id.pawra.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException

class BlogsRepository private constructor(
    private val apiService: ApiService,
) {
    suspend fun getBlogs(user: SessionModel, keyword: String? = ""): Flow<BlogsResponse> {
        try {
            val response = apiService.getBlogs("Bearer ${user.token}", keyword)
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
                BlogsResponse(
                    error = errorMessage
                )
            )

        } catch (e: Exception) {
            return flowOf(
                BlogsResponse(
                    error = e.message
                )
            )
        }
    }

    suspend fun getDetailBlogs(user: SessionModel, blogsId: Int): Flow<BlogsResponseItem> {
        try {
            return flowOf(
                apiService.getDetailBlogs("Bearer ${user.token}", blogsId)
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
                BlogsResponseItem(
                    id = 0,
                    error = errorMessage
                )
            )

        } catch (e: Exception) {
            return flowOf(
                BlogsResponseItem(
                    id = 0,
                    error = e.message
                )
            )
        }
    }

    companion object {
        @Volatile
        private var instance: BlogsRepository? = null
        fun getInstance(
            apiService: ApiService
        ): BlogsRepository =
            instance ?: synchronized(this) {
                instance ?: BlogsRepository(apiService)
            }.also { instance = it }
    }
}
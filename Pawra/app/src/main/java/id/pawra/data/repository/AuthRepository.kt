package id.pawra.data.repository

import com.google.android.gms.common.api.ApiException
import id.pawra.data.local.preference.Preference
import id.pawra.data.local.preference.SessionModel
import id.pawra.data.remote.response.SignInResponse
import id.pawra.data.remote.response.SignUpResponse
import id.pawra.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.MultipartBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException


class AuthRepository private constructor(
    private val apiService: ApiService,
    private val preference: Preference
) {

    suspend fun signIn(username: String, password: String): Flow<SignInResponse> {
        try {
            val signInResponse: SignInResponse = apiService.signIn(username, password)
            saveSession(signInResponse)
            return flowOf(signInResponse)

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                val jsonError = errorBody?.let { JSONObject(it) }
                jsonError?.optString("detail") ?: "Unknown error"
            } catch (jsonException: JSONException) {
                "Error parsing JSON"
            }
            return flowOf(
                SignInResponse(error = errorMessage)
            )

        } catch (e: Exception) {
            return flowOf(
                SignInResponse(error = e.message)
            )
        }
    }


    suspend fun signUp(username: String, email: String, password: String): Flow<SignUpResponse> {
        try {
            val userData = mutableMapOf<String, Any>()
            userData["username"] = username
            userData["email"] = email
            userData["password"] = password

            return flowOf(apiService.signUp(userData))

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                val jsonError = errorBody?.let { JSONObject(it) }
                jsonError?.optString("detail") ?: "Unknown error"
            } catch (jsonException: JSONException) {
                "Error parsing JSON"
            }
            return flowOf(
                SignUpResponse(error = errorMessage)
            )

        } catch (e: Exception) {
            return flowOf(
                SignUpResponse(error = e.message)
            )
        }
    }

    suspend fun updateProfile(
        id: Int,
        token: String,
        username: String = "",
        email: String = "",
        summary: String = "",
        address: String = "",
        image: String = ""
    ): Flow<SignUpResponse> {
        try {
            val userData = mutableMapOf<String, Any>()
            userData["username"] = username
            userData["email"] = email
            userData["summary"] = summary
            userData["address"] = address
            userData["image"] = image

            val response = apiService.updateProfile("Bearer $token", id, userData)
            val sessionModel = SessionModel(
                id = id,
                token = token,
                isLogin = true,
                name = username,
                email = email,
                summary = summary,
                image = image
            )
            preference.saveSession(sessionModel)

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
                SignUpResponse(error = errorMessage)
            )

        } catch (e: Exception) {
            return flowOf(
                SignUpResponse(error = e.message)
            )
        }
    }

    suspend fun postProfileImage(user: SessionModel, file: MultipartBody.Part): Flow<String> {
        try {
            return flowOf(apiService.postProfileImage("Bearer ${user.token}", file))
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

    private suspend fun saveSession(response: SignInResponse) {
        val user = response.user
        val sessionModel = SessionModel(
            id = user?.id ?: 0,
            token = response.accessToken ?: "",
            isLogin = true,
            name = user?.username ?: "",
            email = user?.email ?: "defaultemail@gmail.com",
            summary = user?.summary ?: ("I love dogs and all kind of pets.\n" +
                    "I have one dog, his name is Max. \n" +
                    "He’s been with me for a year.\n" +
                    "Playing catch is our favorite activity"),
            image = user?.image ?: ""
        )
        preference.saveSession(sessionModel)
    }

    fun getSession(): Flow<SessionModel> {
        return preference.getSession()
    }

    suspend fun logout() {
        preference.logout()
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null
        fun getInstance(
            apiService: ApiService,
            preference: Preference
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(apiService, preference)
            }.also { instance = it }
    }
}
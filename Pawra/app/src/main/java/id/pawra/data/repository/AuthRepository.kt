package id.pawra.data.repository

import id.pawra.data.local.preference.Preference
import id.pawra.data.local.preference.SessionModel
import id.pawra.data.remote.response.SignInResponse
import id.pawra.data.remote.response.SignUpResponse
import id.pawra.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.MultipartBody


class AuthRepository private constructor(
    private val apiService: ApiService,
    private val preference: Preference
) {

    suspend fun signIn(username: String, password: String): Flow<SignInResponse> {
        return try {
            val response = apiService.signIn(username, password)
            saveSession(response)
            flowOf(response)
        } catch (e: Exception) {
            flowOf(SignInResponse(error = e.message))
        }
    }


    suspend fun signUp(username: String, email: String, password: String): Flow<SignUpResponse> {
        val userData = mutableMapOf<String, Any>()
        userData["username"] = username
        userData["email"] = email
        userData["password"] = password

        return flowOf(apiService.signUp(userData))
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
    }

    suspend fun postProfileImage(user: SessionModel, file: MultipartBody.Part): Flow<String> {
        return flowOf(apiService.postProfileImage("Bearer ${user.token}", file))
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
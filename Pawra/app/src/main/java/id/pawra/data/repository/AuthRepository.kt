package id.pawra.data.repository

import id.pawra.data.local.preference.Preference
import id.pawra.data.local.preference.SessionModel
import id.pawra.data.remote.response.SignInResponse
import id.pawra.data.remote.response.SignUpResponse
import id.pawra.data.remote.retrofit.ApiService
import id.pawra.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException


class AuthRepository private constructor(
    private val apiService: ApiService,
    private val preference: Preference
) {

    suspend fun signIn(username: String, password: String): Flow<UiState<SignInResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.signIn(username, password)
            saveSession(response)
            emit(UiState.Success(response))
        } catch (e: HttpException) {
            emit(UiState.Error(e.response()?.errorBody()?.string().toString()))
        }
    }


    suspend fun signUp(username: String, email: String, password: String): Flow<UiState<SignUpResponse>> = flow {
        emit(UiState.Loading)
        try {
            val userData = mutableMapOf<String, String>()
            userData["username"] = username
            userData["email"] = email
            userData["password"] = password
            val response = apiService.signUp(userData)
            emit(UiState.Success(response))
        } catch (e: HttpException) {
            emit(UiState.Error(e.response()?.errorBody()?.string().toString()))
        }
    }

    private suspend fun saveSession(response: SignInResponse) {
        val user = response.user
        val sessionModel = SessionModel(
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
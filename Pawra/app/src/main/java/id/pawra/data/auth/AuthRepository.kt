package id.pawra.data.auth

import com.google.gson.Gson
import id.pawra.data.local.preference.Preference
import id.pawra.data.local.preference.SessionModel
import id.pawra.data.remote.response.SignInResponse
import id.pawra.data.remote.response.SignInResult
import id.pawra.data.remote.response.SignUpResponse
import id.pawra.data.remote.retrofit.ApiService
import id.pawra.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class AuthRepository private constructor(
    private val apiService: ApiService,
    private val preference: Preference
) {

    suspend fun signIn(email: String, password: String): Flow<UiState<SignInResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.signIn(email, password)
            val loginResult = response.loginResult

            if (loginResult != null) {
                saveSession(loginResult)
                emit(UiState.Success(response))
            } else {
                emit(UiState.Error("Failed to retrieve login result"))
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, SignInResponse::class.java)
            emit(UiState.Error(errorBody.message.toString()))
        }
    }


    suspend fun signUp(name: String, email: String, password: String): Flow<UiState<SignUpResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.signUp(name, email, password)
            emit(UiState.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, SignUpResponse::class.java)
            emit(UiState.Error(errorBody.message.toString()))
        }
    }

    private suspend fun saveSession(loginResult: SignInResult) {
        val sessionModel = SessionModel(
            token = loginResult.token ?: "",
            isLogin = true,
            name = loginResult.name ?: "",
            email = loginResult.email ?: "defaultemail@gmail.com",
            summary = loginResult.summary ?: ("I love dogs and all kind of pets.\n" +
                    "I have one dog, his name is Max. \n" +
                    "He’s been with me for a year.\n" +
                    "Playing catch is our favorite activity")
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
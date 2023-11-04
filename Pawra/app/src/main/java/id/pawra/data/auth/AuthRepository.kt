package id.pawra.data.auth

import com.google.gson.Gson
import id.pawra.data.local.preference.Preference
import id.pawra.data.local.preference.SessionModel
import id.pawra.data.remote.response.SignInResponse
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

    fun signIn(email: String, password: String) = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.signIn(email, password)
            emit(UiState.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, SignInResponse::class.java)
            emit(UiState.Error(errorBody.message.toString()))
        }
    }

    suspend fun signUp(name: String, email: String, password: String) = flow {
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

    suspend fun saveSession(user: SessionModel) {
        preference.saveSession(user)
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
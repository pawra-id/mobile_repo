package id.pawra.data.repository

import id.pawra.data.local.preference.Preference
import id.pawra.data.remote.response.PetResponse
import id.pawra.data.remote.response.PetResponseItem
import id.pawra.data.remote.response.SignInResponse
import id.pawra.data.remote.retrofit.ApiService
import id.pawra.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class PetRepository private constructor(
    private val apiService: ApiService,
) {
    suspend fun getDog(token: String): Flow<UiState<List<PetResponseItem>>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.getDogs("Bearer $token")
            emit(UiState.Success(response))
        } catch (e: HttpException) {
            emit(UiState.Error(e.response()?.errorBody()?.string().toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: PetRepository? = null
        fun getInstance(
            apiService: ApiService
        ): PetRepository =
            instance ?: synchronized(this) {
                instance ?: PetRepository(apiService)
            }.also { instance = it }
    }
}
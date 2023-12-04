package id.pawra.data.repository

import id.pawra.data.local.preference.PetData
import id.pawra.data.local.preference.SessionModel
import id.pawra.data.remote.response.PetResponseItem
import id.pawra.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.MultipartBody

class PetRepository private constructor(
    private val apiService: ApiService,
) {
    suspend fun getDog(user: SessionModel): Flow<List<PetResponseItem>> {
        return flowOf(apiService.getDogs("Bearer ${user.token}"))
    }

    suspend fun getDetailDog(user: SessionModel, petId: Int): Flow<PetResponseItem> {
        return flowOf(apiService.getDetailDog("Bearer ${user.token}", petId))
    }

    suspend fun addDog(user: SessionModel, petData: PetData): Flow<PetResponseItem> {
        val userData = mutableMapOf<String, Any>()
        userData["name"] = petData.name
        userData["gender"] = petData.gender
        userData["age"] = petData.age
        userData["breed"] = petData.breed
        userData["neutered"] = petData.neutred
        userData["color"] = petData.primaryColor
        userData["weight"] = petData.weight
        userData["height"] = petData.height
        userData["description"] = petData.summary.toString()
        userData["image"] = petData.image

        return flowOf(apiService.addDog("Bearer ${user.token}", userData))
    }

    suspend fun postDogImage(user: SessionModel, file: MultipartBody.Part): Flow<String> {
        return flowOf(apiService.postDogImage("Bearer ${user.token}", file))
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
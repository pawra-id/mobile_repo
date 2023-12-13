package id.pawra.di

import android.content.Context
import id.pawra.data.repository.AuthRepository
import id.pawra.data.local.preference.Preference
import id.pawra.data.local.preference.dataStore
import id.pawra.data.remote.retrofit.ApiConfig
import id.pawra.data.remote.retrofit.ApiService
import id.pawra.data.repository.ActivitiesRepository
import id.pawra.data.repository.BlogsRepository
import id.pawra.data.repository.PetRepository
import id.pawra.data.repository.VetRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {

    private fun getApiService(context: Context): ApiService {

        return ApiConfig.getApiService(context)
    }

    fun provideAuthRepository(context: Context): AuthRepository {
        val apiService = getApiService(context)
        return AuthRepository.getInstance(apiService, Preference.getInstance(context.dataStore))
    }

    fun providePetRepository(context: Context): PetRepository {
        val apiService = getApiService(context)
        return PetRepository.getInstance(apiService)
    }

    fun provideActivitiesRepository(context: Context): ActivitiesRepository {
        val apiService = getApiService(context)
        return ActivitiesRepository.getInstance(apiService)
    }

    fun provideVetRepository(context: Context): VetRepository {
        val apiService = getApiService(context)
        return VetRepository.getInstance(apiService)
    }

    fun provideBlogsRepository(context: Context): BlogsRepository {
        val apiService = getApiService(context)
        return BlogsRepository.getInstance(apiService)
    }
}
package id.pawra.di

import android.content.Context
import id.pawra.data.repository.AuthRepository
import id.pawra.data.local.preference.Preference
import id.pawra.data.local.preference.dataStore
import id.pawra.data.remote.retrofit.ApiConfig
import id.pawra.data.remote.retrofit.ApiService
import id.pawra.data.repository.ActivitiesRepository
import id.pawra.data.repository.BlogsRepository
import id.pawra.data.repository.AnalysisRepository
import id.pawra.data.repository.PetRepository
import id.pawra.data.repository.VetRepository

object Injection {

    private fun getApiService(): ApiService {
        return ApiConfig.getApiService()
    }

    fun provideAuthRepository(context: Context): AuthRepository {
        val apiService = getApiService()
        return AuthRepository.getInstance(apiService, Preference.getInstance(context.dataStore))
    }

    fun providePetRepository(): PetRepository {
        val apiService = getApiService()
        return PetRepository.getInstance(apiService)
    }

    fun provideActivitiesRepository(): ActivitiesRepository {
        val apiService = getApiService()
        return ActivitiesRepository.getInstance(apiService)
    }

    fun provideVetRepository(): VetRepository {
        val apiService = getApiService()
        return VetRepository.getInstance(apiService)
    }

    fun provideBlogsRepository(): BlogsRepository {
        val apiService = getApiService()
        return BlogsRepository.getInstance(apiService)
    }

    fun provideAnalysisRepository(): AnalysisRepository {
        val apiService = getApiService()
        return AnalysisRepository.getInstance(apiService)
    }
}
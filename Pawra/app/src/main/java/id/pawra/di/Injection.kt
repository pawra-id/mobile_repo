package id.pawra.di

import android.content.Context
import id.pawra.data.auth.AuthRepository
import id.pawra.data.local.preference.Preference
import id.pawra.data.local.preference.dataStore
import id.pawra.data.remote.retrofit.ApiConfig
import id.pawra.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {

    private fun getApiService(context: Context): ApiService {
        val userPreference = Preference.getInstance(context.dataStore)
        val user = runBlocking { userPreference.getSession().first() }
        return ApiConfig.getApiService(user.token)
    }

    fun provideAuthRepository(context: Context): AuthRepository {
        val apiService = getApiService(context)
        return AuthRepository.getInstance(apiService, Preference.getInstance(context.dataStore))
    }
}
package id.pawra.data.remote.retrofit

import android.content.Context
import id.pawra.BuildConfig.DEBUG
import id.pawra.data.local.preference.Preference
import id.pawra.data.local.preference.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    companion object {

        private var BASE_URL = "https://deb8-103-162-237-58.ngrok-free.app/"
//        private var BASE_URL = "https://pawra-backend-api-2gso7b5r3q-et.a.run.app/"

        fun getApiService(context: Context): ApiService {

            val loggingInterceptor = if(DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            val userPreference = Preference.getInstance(context.dataStore)
            val user = runBlocking { userPreference.getSession().first() }

//            val authInterceptor = Interceptor { chain ->
//                val req = chain.request()
//                val requestHeaders = req.newBuilder()
//                    .addHeader("Authorization", "Bearer ${user.token}")
//                    .build()
//                chain.proceed(requestHeaders)
//            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
//                .addInterceptor(authInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
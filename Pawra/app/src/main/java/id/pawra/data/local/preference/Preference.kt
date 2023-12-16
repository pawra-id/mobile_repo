package id.pawra.data.local.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class Preference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(user: SessionModel) {
        dataStore.edit { preferences ->
            preferences[ID] = user.id
            preferences[TOKEN_KEY] = user.token
            preferences[IS_LOGIN_KEY] = true
            preferences[NAME_KEY] = user.name
            preferences[EMAIL_KEY] = user.email
            preferences[SUMMARY_KEY] = user.summary
            preferences[IMAGE] = user.image
            preferences[LATITUDE] = user.latitude
            preferences[LONGITUDE] = user.longitude
            preferences[EXPIRE] = user.expire
            preferences[IS_LAUNCHED] = user.isLaunched
        }
    }

    fun getSession(): Flow<SessionModel> {
        return dataStore.data.map { preferences ->
            SessionModel(
                preferences[ID] ?: 0,
                preferences[TOKEN_KEY] ?: "",
                preferences[NAME_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[SUMMARY_KEY] ?: "",
                preferences[IMAGE] ?: "",
                preferences[LATITUDE] ?: "",
                preferences[LONGITUDE] ?: "",
                preferences[EXPIRE] ?: "",
                preferences[IS_LOGIN_KEY] ?: false,
                preferences[IS_LAUNCHED] ?: false,
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: Preference? = null

        private val ID = intPreferencesKey("id")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val SUMMARY_KEY = stringPreferencesKey("summary")
        private val IMAGE = stringPreferencesKey("image")
        private val LATITUDE = stringPreferencesKey("LATITUDE")
        private val LONGITUDE = stringPreferencesKey("LONGITUDE")
        private val EXPIRE = stringPreferencesKey("EXPIRE")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")
        private val IS_LAUNCHED = booleanPreferencesKey("isLaunched")
        private val IS_FIRST_LAUNCHED = booleanPreferencesKey("isFirstLaunched")

        fun getInstance(dataStore: DataStore<Preferences>): Preference {
            return INSTANCE ?: synchronized(this) {
                val instance = Preference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
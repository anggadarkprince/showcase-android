package com.anggaari.showcase.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.anggaari.showcase.models.auth.User
import com.anggaari.showcase.utils.Constants.Companion.PREFERENCES_ACCESS_TOKEN
import com.anggaari.showcase.utils.Constants.Companion.PREFERENCES_NAME
import com.anggaari.showcase.utils.Constants.Companion.PREFERENCES_USER
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

@Singleton
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private val dataStore = context.dataStore

    private object PreferenceKeys {
        val accessToken = stringPreferencesKey(PREFERENCES_ACCESS_TOKEN)
        val user = stringPreferencesKey(PREFERENCES_USER)
    }

    suspend fun saveAccessToken(accessToken: String) {
        Log.d("DataStoreRepository.saveAccessToken", accessToken)
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.accessToken] = accessToken
            Log.d("DataStoreRepository.edit", preferences.toString())
        }
    }

    suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.user] = Gson().toJson(user)
        }
    }

    val user: Flow<User> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            Log.d("DataStoreRepository.user", preferences.toString())

            val user = preferences[PreferenceKeys.user] ?: ""

            Gson().fromJson(user, User::class.java)
        }

    val accessToken: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences -> preferences[PreferenceKeys.accessToken] ?: "" }
}
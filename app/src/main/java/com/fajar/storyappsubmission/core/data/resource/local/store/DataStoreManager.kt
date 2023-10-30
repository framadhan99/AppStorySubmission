package com.fajar.storyappsubmission.core.data.resource.local.store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore("data")

class DataStoreManager(appContext: Context) {

    private val userDataStore = appContext.dataStore

    companion object {
        val USER_TOKEN_KEY = stringPreferencesKey("USER_TOKEN")
    }

    suspend fun storeSession(token: String) {
        userDataStore.edit { data ->
            data[USER_TOKEN_KEY] = token
        }
    }

    suspend fun deleteSession() {
        userDataStore.edit { data ->
            data.clear()
        }
    }

    val tokenUser: Flow<String> = userDataStore.data.map { data ->
        data[USER_TOKEN_KEY] ?: String()
    }


}
package com.fajar.storyappsubmission.core.data.resource.remote.auth

import com.fajar.storyappsubmission.core.data.model.User
import com.fajar.storyappsubmission.core.data.resource.local.store.DataStoreManager
import com.fajar.storyappsubmission.core.data.resource.local.store.UserPreferences
import com.fajar.storyappsubmission.core.data.resource.remote.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthSource @Inject constructor(
    private val authService: AuthServices,
    private val dataStoreManager: DataStoreManager
) {



    suspend fun registerUser(authBody: AuthBody): Flow<ApiResult<AuthResponse>> {
        return flow {
            try {
                emit(ApiResult.loading())
                val response = authService.registerUser(authBody)
                if (!response.error) {
                    emit(ApiResult.success(response))
                } else {
                    emit(ApiResult.error(response.message))
                }
            } catch (ex: Exception) {
                emit(ApiResult.error(ex.message.toString()))
            }
        }
    }

    suspend fun loginUser(authBody: AuthBody): Flow<ApiResult<AuthResponse>> {
        return flow {
            try {
                emit(ApiResult.loading())
                val response = authService.loginUser(authBody)
                if (!response.error) {
                    dataStoreManager.storeSession(response.loginResult.token)
//
                    emit(ApiResult.success(response))
                } else {
                    emit(ApiResult.error(response.message))
                }
            } catch (ex: Exception) {
                emit(ApiResult.error(ex.message.toString()))
            }
        }
    }

}
package com.fajar.storyappsubmission.core.data.resource.remote.auth

import com.fajar.storyappsubmission.core.data.resource.remote.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepo @Inject constructor(private val authDataSource: AuthSource) {

    suspend fun registerUser(authBody: AuthBody): Flow<ApiResult<AuthResponse>> {
        return authDataSource.registerUser(authBody).flowOn(Dispatchers.IO)
    }

    suspend fun loginUser(authBody: AuthBody): Flow<ApiResult<AuthResponse>> {
        return authDataSource.loginUser(authBody).flowOn(Dispatchers.IO)
    }

}
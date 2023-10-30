package com.fajar.storyappsubmission.core.data.resource.remote.auth

import com.fajar.storyappsubmission.core.data.resource.remote.auth.AuthBody
import com.fajar.storyappsubmission.core.data.resource.remote.auth.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthServices {

    @POST("register")
    suspend fun registerUser(
        @Body authBody: AuthBody
    ): AuthResponse

    @POST("login")
    suspend fun loginUser(
        @Body authBody: AuthBody
    ): AuthResponse

}
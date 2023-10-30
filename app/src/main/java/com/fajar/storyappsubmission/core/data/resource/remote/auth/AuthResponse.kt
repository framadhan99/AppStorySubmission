package com.fajar.storyappsubmission.core.data.resource.remote.auth

import com.fajar.storyappsubmission.core.data.model.User

data class AuthResponse(

    val error: Boolean,
    val message: String,
    val loginResult: User
)
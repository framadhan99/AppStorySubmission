package com.fajar.storyappsubmission.core.data.resource.remote.auth

data class AuthBody(
    val name: String? = null,
    val email: String,
    val password: String
)
package com.example.gonggaksim_frontend

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val accessToken: String?,
    val refreshToken: String?,
    val isNewUser: Boolean
)

package com.example.gonggaksim_frontend

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import java.security.Provider

interface ApiService {
    @POST("/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}

data class LoginRequest(
    val token: String
)

data class LoginResponse(
    val userId: String,
    val userEmail: String,
    val accessToken: String,
    val refreshToken: String,
//    val oauthProvider: Provider,
    val isNewUser: Boolean
)
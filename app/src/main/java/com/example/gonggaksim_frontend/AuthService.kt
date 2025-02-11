package com.example.gonggaksim_frontend

import retrofit2.http.Header
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface AuthService {
    @POST("/oauth2/login/google")
    fun loginWithGoogle(@Body token: TokenRequest): Call<LoginResponse>
}

data class TokenRequest(val idToken: String)

package com.example.gonggaksim_frontend

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService_try {
    @POST("google-login")
    fun loginWithGoogle(@Body request: GoogleLoginRequest): Call<LoginResponse>
}
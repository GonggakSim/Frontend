package com.example.gonggaksim_frontend

import retrofit2.http.Header
import retrofit2.Call
import retrofit2.http.POST

interface AuthService {
    @POST("/") // BASE_URL에 바로 붙이기 위해 "."
    fun loginWithGoogle(@Header("Authorization") token: String): Call<LoginResponse>
}
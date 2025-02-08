package com.example.gonggaksim_frontend

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.Call

interface AuthService {
    @GET("outh2/login/kakao")
    fun loginWithGoogle(
        @Header("Authorization") token: String
    ): Call<LoginResponse>
}
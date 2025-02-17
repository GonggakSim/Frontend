package com.example.gonggaksim_frontend

import retrofit2.Call // ✅ 올바른 import 추가
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
     @GET("api/v1/users/mypage")
     fun getUserMypage(
         @Header("Authorization") authToken: String,  // OAuth2 토큰 인증
         @Query("provider") provider: String  // Google, Kakao 등
     ): Call<UserResponse>

    @GET("api/v1/users/mypage")
    fun getUserInfo(
        @Header("Authorization") token: String
    ): Call<UserResponse>

    // 회원가입
    @POST("/oauth2/register")
    fun signup(@Body request: SignupRequest): Call<SignupResponse>

    @POST("/oauth2/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}

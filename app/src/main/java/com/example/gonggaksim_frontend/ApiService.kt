package com.example.gonggaksim_frontend

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
     @GET("api/v1/users/mypage")
     fun getUserMypage(
         @Header("Authorization") authToken: String,  // OAuth2 토큰 인증
         @Query("provider") provider: String  // Google, Kakao 등
     ): retrofit2.Call<UserResponse>

    @GET("api/v1/users/mypage")
    fun getUserInfo(
        @Header("Authorization") token: String
    ): retrofit2.Call<UserResponse>

    @GET("oauth2/login/kakao")
    fun kakaoLogin(
        @Header("Authorization") authToken: String,  // OAuth2 토큰 인증
    ): Call<KakaoResponse>

    @GET("api/v1/certifications")
    fun getCertifications(
        @Header("Authorization") token: String
    ):  retrofit2.Call<List<Certification>>
}

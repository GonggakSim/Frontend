package com.example.gonggaksim_frontend

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Query
import retrofit2.http.Body

interface mypageService {
     @GET("api/v1/users/mypage")
     fun getUserMypage(
         @Header("Authorization") authToken: String,  // OAuth2 토큰 인증
         @Query("provider") provider: String  // Google, Kakao 등
     ):Call<UserResponse>

    @PATCH("api/v1/users/mypage/user-edit")
    fun modifyProfile(
        @Header("Authorization") authToken : String,  // OAuth2 토큰 인증
        @Query("provider") provider : String,  //
        @Body request : UserModifyData
    ):Call<UserResponse>
}

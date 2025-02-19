package com.example.gonggaksim_frontend

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Query
import retrofit2.http.Body
import retrofit2.http.Path

interface mypageService {
     @GET("api/v1/users/mypage")
     fun getUserMypage(
         @Header("Authorization") authToken: String,  // OAuth2 토큰 인증
         @Query("provider") provider: String  // Google, Kakao 등
     ):Call<UserResponseMypage>

    @PATCH("api/v1/users/mypage/user-edit")
    fun modifyProfile(
        @Header("Authorization") authToken : String,  // OAuth2 토큰 인증
        @Query("provider") provider : String,  //
        @Body request : UserModifyData
    ):Call<UserResponseModify>
}

interface certificateService{
    @GET("api/v1/certifications")
    fun getAllCertifies(
        @Header("Authorization") authToken : String,  // OAuth2 토큰 인증
        @Query("provider") provider : String,  //
    ):Call<UserResponseCertification>
    @GET("api/v1/certifications/category/{category}")
    fun getCategoryCertifies(
        @Header("Authorization") authToken: String,
        @Path("category") category: String,  // @Path가 @Query보다 먼저 정의되어야 함
        @Query("provider") provider: String
    ): Call<UserResponseCertification>
    @GET("api/v1/certifications/{certificationId}")
    fun getCertificationDetails(
        @Header("Authorization") authToken : String,  // OAuth2 토큰 인증
        @Query("provider") provider : String,  //
        @Path("certificationId") category: String
    ):Call<UserResponseDetail>
}

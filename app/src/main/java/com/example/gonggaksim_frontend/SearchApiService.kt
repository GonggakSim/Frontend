package com.example.gonggaksim_frontend

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchApiService {
    @GET("api/v1/certifications/search")  // Swagger 문서에서 API 엔드포인트 확인 후 변경
    fun searchCertificates(
        @Header("Authorization") authToken: String,
        @Query("query") query: String,
        @Query("provider") provider: String? = null,
        @Query("category") category: String? = null
    ): Call<SearchResponse>
}

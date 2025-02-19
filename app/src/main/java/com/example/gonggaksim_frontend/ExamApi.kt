package com.example.gonggaksim_frontend

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ExamApi {
    @GET("exams/register/{certificationId}")
    fun registerExam(
        @Path("certificationId") certificationId: String, // 필수 경로 파라미터
        @Query("provider") provider: String // 선택적 쿼리 파라미터
    ): Call<ExamResponse>
}

package com.example.gonggaksim_frontend.api


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ScheduleApiService {
    @POST("/api/v1/schedule/recommendation")
    fun getExamRecommendation(
        @Query("provider") provider: String,
        @Body requestBody: ExamRecommendationRequest
    ): Call<ExamRecommendationResponse>
}

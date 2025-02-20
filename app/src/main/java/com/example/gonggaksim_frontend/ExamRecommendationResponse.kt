package com.example.gonggaksim_frontend.api

data class ExamRecommendationResponse(
    val status: String,
    val message: String,
    val data: ExamData?
)

data class ExamData(
    val userId: Int,
    val recommendedPlan: String,
    val examDate: String
)

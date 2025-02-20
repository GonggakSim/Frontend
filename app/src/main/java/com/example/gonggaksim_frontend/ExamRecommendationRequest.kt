package com.example.gonggaksim_frontend.api

data class ExamRecommendationRequest(
    val userId: Int,
    val name: String,
    val studyExperience: String,
    val studyTimePerDay: String,
    val studyFrequency: String,
    val examGoal: String
)

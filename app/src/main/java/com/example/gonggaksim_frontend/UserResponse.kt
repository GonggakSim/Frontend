package com.example.gonggaksim_frontend

data class UserResponse(
    val success: Boolean,
    val message: String,
    val data: UserData?
)

data class UserData(
    val name: String,
    val profileImage: String,
    val categories: List<String>,
    val closestExams: List<Exam>?,
    val certifications: List<Certification>?
)

data class Exam(
    val name: String,
    val date: String,
    val dDay: Int
)

data class Certification(
    val id: String,
    val name: String,
    val category: String
)

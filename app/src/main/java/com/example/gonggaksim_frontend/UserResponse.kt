package com.example.gonggaksim_frontend

data class UserResponse(
    val success: Boolean,
    val message: String,
    val data: UserData?
)

data class UserData(
    val name: String,
    val profileImage: String,
    val categories: List<String>?,
    val closestExams: List<Exam>?
)

data class Exam(
    val name: String,
    val date: String,
    val dDay: Int
)

data class UserModifyData(
    val age : Int,
    val department : String,
    val grade : String,
    val category : List<String>,
    val employmentStatus : String,
    val employCategory : String
)

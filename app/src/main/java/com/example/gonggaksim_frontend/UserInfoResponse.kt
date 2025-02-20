package com.example.gonggaksim_frontend

data class UserInfoResponse(
    val success: Boolean,
    val message: String,
    val result: UserInfoResult?
)

data class UserInfoResult(
    val id: Int,
    val name: String,
    val email: String,
    val employmentStatus: String,
    val employCategory: String,
    val age: Int,
    val department: String,
    val grade: String
)
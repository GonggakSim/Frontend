package com.example.gonggaksim_frontend

data class UserInfoRequest(
    val age: Int,
    val department: String,
    val grade: String,
    val category: List<String>,
    val employmentStatus: String,
    val employCategory: String
)
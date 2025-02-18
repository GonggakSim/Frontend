package com.example.gonggaksim_frontend

data class LoginResponse(
    val success: Boolean,
    val masseage: Message?
)

data class Message(
    val id: Int,
    val email: String,
    val token: String
)


package com.example.gonggaksim_frontend

data class Message(
    val id: Int,
    val email: String,
    val token: String
)

data class LoginResponse(
    val success: Boolean,
    val message: Message
)

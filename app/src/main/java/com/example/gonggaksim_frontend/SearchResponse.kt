package com.example.gonggaksim_frontend

data class SearchResponse(
    val success: Boolean,
    val message: String,
    val data: List<Certificate>
)

data class Certificate(
    val id: Int,
    val name: String,
    val category: String
)


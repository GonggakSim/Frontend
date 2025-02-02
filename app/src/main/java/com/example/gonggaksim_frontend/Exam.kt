package com.example.gonggaksim_frontend

data class Exam(
    val category: String,
    val name: String,
    val qualification: String,
    val qualificationDetail: String,
    val subjects: String,
    val questionFormat: String,
    val duration: String,
    val passingCriteria: String,
    val fee: String,
    val announcement: String
)

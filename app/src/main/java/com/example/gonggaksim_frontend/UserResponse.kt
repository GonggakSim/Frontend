package com.example.gonggaksim_frontend

data class UserResponse(
    val success: Boolean,
    val message: String,
    val data: UserData?,
)

data class UserData(
    val name: String?,
    val profileImage: String?,
    val categories: List<String>?,
    val closestExams: List<Exam>?,
    val certifications: List<Certification>? // 자격증 정보
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

data class KakaoResponse(
    val success: Boolean,
    val message: String,
    val accessToken: String,   // 서버 응답에 포함된 필드
    val refreshToken: String,  // 서버 응답에 포함된 필드
    val isNewUser: Boolean      // 신규 사용자 여부
)

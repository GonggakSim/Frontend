package com.example.gonggaksim_frontend

data class UserResponseMypage(
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

data class UserResponseModify(
    val success: Boolean,
    val message: String,
    val data: UserModifyData?
)
data class UserModifyData(
    val age : Int,
    val department : String,
    val grade : String,
    val category : List<String>,
    val employmentStatus : String,
    val employCategory : String
)
data class Certification(
    val certification_id: Int,
    val name: String,
    val category: String
)
data class UserResponseCertification(
    val success: Boolean,
    val message: String,
    val data: Certification?
)
data class UserResponseDetail(
    val success: Boolean,
    val message: String,
    val data: CertificationDetail?
)
data class CertificationDetail(
    val certification_id: Int,
    val name: String,
    val category: String,
    val eligibility: String,
    val subjects: String,
    val examFormat: String,
    val examDuration: String,
    val passingCriteria: String,
    val fee: String,
    val announcementSchedule: String
)

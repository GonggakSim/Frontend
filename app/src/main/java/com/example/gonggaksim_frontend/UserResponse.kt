package com.example.gonggaksim_frontend

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val success: Boolean, //+
    val message: String, //+
    val data: UserData?,  //+
    val id: Int,
    val email: String,
    val name: String,
    val profileImageUrl: String?
)

data class UserResponseMypage(
    val success: Boolean,
    val message: String,
    val data: UserData?
)

data class UserData(
    val name: String,
    val profileImage: String,
    val categories: List<String>,
    val closestExams: List<Exam>?,
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
data class UserResponseCertification(
    val success: Boolean,
    val message: String,
    val data: List<Certification> // ✅ data 필드가 리스트로 들어감
)

data class Certification(
    @SerializedName("id") val certificationId: Int,
    val name: String,
    val category: String
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

data class UserResponseIdMonth(
    val dates: List<Dates>,
    val message: String
)

data class Dates(
    val date: String,
    val scheduleId: Int
)

data class UserResponseNotifications(
    val success: Boolean,
    val message: String
)


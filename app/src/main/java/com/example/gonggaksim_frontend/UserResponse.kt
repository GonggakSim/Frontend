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
// 최상위 응답 클래스
data class UserResponseQuiz(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val dataWrapper: QuizDataWrapper?
)

// 중첩된 데이터 래퍼
data class QuizDataWrapper(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val quizData: QuizData?
)

// 퀴즈 데이터 클래스
data class QuizData(
    @SerializedName("_id") val id: String = "",
    @SerializedName("question_id") val questionId: String = "",
    @SerializedName("certification_name") val certificationName: String = "",
    @SerializedName("quiz_type") val quizType: String = "",
    @SerializedName("answer") val answer: String = "",
    @SerializedName("options") val options: List<String> = emptyList(),
    @SerializedName("question") val question: String = "",
    @SerializedName("subject") val subject: String = ""
)

data class QuizSettings(
    val certifications : List<String>,
    val quizTypes : List<String>,
    val subjects : List<String>,
    val userId : Int
)

data class UserResponseIdMonth(
    val dates: List<Dates>,
    val message: String
)

data class Dates(
    val date: String,
    val scheduleId: Int
)
data class UserRequestNotifications(
    val scheduleId: Int,
    val userId: Int
)

data class UserResponseNotifications(
    val success: Boolean,
    val message: String,
    val data: DataNoti,
)

data class DataNoti(
    val id: Int,
    val scheduleId: Int,
    val alramState: Boolean,
    val createAt: String,
    val updateAt: String
)

data class UserResponseScaduleCheck(
    val success: Boolean,
    val message: String,
    val examLink: String,
)

data class UserCalendarExamInput(
    val success: Boolean,
    val message: String,
    val data: CalendarData,

)

data class CalendarData(
    val id: Int,
    val userId: Int,
    val title: String,
    val examStart: String,
    val examEnd: String,
    val remindState: Boolean,
    val createAt: String,
    val updateAt: String,
)

data class CalendarUsers(
    val success: Boolean,
    val data: List<CalendarUserData>
)

data class CalendarUserData(
    val title: String,
    val examStart: String,
    val examEnd: String,
    val remindState: Boolean,
)

data class DeleteCalendarExam(
    val success: Boolean,
    val message: String
)

data class logoutResponse(
    val success: Boolean,
    val message: String?,
    val reason : String?
)

data class accountDeleteResponse(
    val success: Boolean,
    val message: String?
)
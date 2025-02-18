package com.example.gonggaksim_frontend

data class DateEvent(
    var date: ArrayList<String> = arrayListOf<String>(
        "2025년 02월 02일 2025년 02월 04일 F27969 중간고사",
        "2025년 02월 05일 2025년 02월 09일 A769F2 토익",
        "2025년 02월 15일 2025년 02월 15일 6996F2 정보처리기능사",
        "2025년 02월 20일 2025년 02월 20일 F27969 중간고사",
        "2025년 02월 23일 2025년 02월 23일 6996F2 중간고사",
        "2025년 02월 25일 2025년 02월 25일 A769F2 중간고사",
        "2025년 03월 02일 2025년 03월 02일 F27969 중간고사",

    )
) {
    fun addEvent(event: String) {
        date.add(event)
    }
}

object DateEventSingleton {
    val dateEvent = DateEvent()
}

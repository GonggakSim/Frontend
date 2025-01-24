package com.example.gonggaksim_frontend

object DataProvider {
    // 모든 시험 데이터
    val allData = listOf(
        "컴퓨터활용능력 1급 필기", "MOS", "SQLD", "빅데이터분석기사",
        "정보처리기사", "워드프로세서", "ITQ", "전산회계", "회계관리",
        "경영정보학", "유통관리사", "시각디자인기사", "산업디자인기사", "TOEIC", "TOEFL", "IELTS",
        "JLPT", "JPT", "전산회계 1급", "회계관리 1급", "경영지도사", "경영정보학", "기타 시험 1", "기타 시험 2"
    )

    // 카테고리별 데이터
    val categoryDataMap = mapOf(
        "All" to allData,
        "IT" to listOf("컴퓨터활용능력 1급", "MOS", "ITQ"),
        "English" to listOf("TOEIC", "TOEFL", "IELTS"),
        "Japanese" to listOf("JLPT", "JPT"),
        "Design" to listOf("시각디자인기사", "산업디자인기사"),
        "Accounting" to listOf("전산회계 1급", "회계관리 1급"),
        "Management" to listOf("경영지도사", "경영정보학"),
        "ITAdvanced" to listOf("정보처리기사", "컴퓨터활용능력 1급"),
        "Logistics" to listOf("물류관리사", "유통관리사"),
        "Others" to listOf("기타 시험 1", "기타 시험 2")
    )
}

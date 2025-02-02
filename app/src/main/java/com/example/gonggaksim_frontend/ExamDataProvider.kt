package com.example.gonggaksim_frontend

object ExamDataProvider {
    val exams = mapOf(
        "정보처리기사" to Exam(
            category = "전산/IT",
            name = "정보처리기사",
            qualification = "제한없음",
            qualificationDetail = "단, 실기시험은 필기시험 합격 후 2년 이내에 지원 가능",
            subjects = "데이터베이스\n 소프트웨어 설계\n 네트워크\n",
            questionFormat = "객관식 80문항",
            duration = "120분",
            passingCriteria = "과목당 40점 이상\n 평균 60점 이상",
            fee = "25,700원",
            announcement = "시험일 다음날 오전 10시"
        ),
        "컴퓨터활용능력 1급 필기" to Exam(
            category = "전산/IT",
            name = "컴퓨터활용능력 1급 필기",
            qualification = "제한없음",
            qualificationDetail = "단, 실기시험은 필기시험 합격 후 2년 이내에 지원 가능",
            subjects = "데이터베이스\n 소프트웨어 설계\n 네트워크\n",
            questionFormat = "객관식 80문항",
            duration = "120분",
            passingCriteria = "과목당 40점 이상\n 평균 60점 이상",
            fee = "25,700원",
            announcement = "시험일 다음날 오전 10시"
        ),
        "전산회계" to Exam(
            category = "회계/재무",
            name = "전산회계",
            qualification = "제한없음",
            qualificationDetail = "단, 실기시험은 필기시험 합격 후 2년 이내에 지원 가능",
            subjects = "회계원리\n" + " 원가관리회계\n" + "세무회계",
            questionFormat = "객관식 80문항",
            duration = "90분",
            passingCriteria = "과목당 40점 이상\n 평균 60점 이상",
            fee = "30,000원",
            announcement = "시험일 다음날 오전 10시"
        )
    )
}

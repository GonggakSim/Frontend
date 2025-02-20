//package com.example.gonggaksim_frontend
//
//import android.os.Bundle
//import android.view.View
//import android.widget.Button
//import android.widget.TextView
//import androidx.activity.viewModels
//import androidx.appcompat.app.AppCompatActivity
//
//class ExamActivity : AppCompatActivity() {
//    private val examViewModel: ExamViewModel by viewModels()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_exam)
//
//        val registerButton = findViewById<Button>(R.id.registerButton)
//        val successMessage = findViewById<TextView>(R.id.successMessage)
//        val errorMessage = findViewById<TextView>(R.id.errorMessage)
//
//        // 버튼 클릭 시 API 호출
//        registerButton.setOnClickListener {
//            examViewModel.registerExam("12345", "providerName")  // 인증 ID와 제공자 이름 예시로 사용
//
//            // 로딩 상태 등 다른 UI 업데이트가 필요하면 여기서 처리
//        }
//
//        // 성공 메시지 업데이트
//        examViewModel.examResponse.observe(this, { response ->
//            response?.let {
//                if (it.success) {
//                    successMessage.text = it.message
//                    successMessage.visibility = View.VISIBLE
//                    errorMessage.visibility = View.GONE
//                }
//            }
//        })
//
//        // 에러 메시지 업데이트
//        examViewModel.error.observe(this, { error ->
//            errorMessage.text = error
//            errorMessage.visibility = View.VISIBLE
//            successMessage.visibility = View.GONE
//        })
//    }
//}

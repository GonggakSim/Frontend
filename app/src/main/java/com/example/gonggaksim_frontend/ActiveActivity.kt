package com.example.gonggaksim_frontend

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActiveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_active)

        // 새로운 Spinner 추가
        val workSpinner2: Spinner = findViewById(R.id.spinner_work2)
        val newSpinner: Spinner = findViewById(R.id.spinner_type)


        // 새로운 Spinner에 설정할 데이터 리스트
        val wokList = listOf("재직 중")
        val newOptions = listOf("재직 중인 업종을 선택해 주세요","IT/개발", "디자인", "금융/회계", "마케팅/홍보", "물류/유통", "생산,품질관리")

        // 어댑터 설정
        val workAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, wokList)
        workAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val newSpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, newOptions)
        newSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // 새로운 Spinner에 어댑터 연결
        workSpinner2.adapter = workAdapter
        newSpinner.adapter = newSpinnerAdapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
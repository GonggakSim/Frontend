package com.example.gonggaksim_frontend

import OnboardingPopUp
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WorkActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_work)

        val workSpinner: Spinner = findViewById(R.id.spinner_work)
        val nextButton: Button = findViewById(R.id.btn_next)

        // 데이터 리스트 설정
        val workList = listOf("작업을 선택해 주세요", "재직 중", "퇴사 예정", "구직 중")

        // 어댑터 설정
        val workAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, workList)
        workAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // 스피너 어댑터 연결
        workSpinner.adapter = workAdapter

        // 버튼 활성화 로직
        workSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedWork = workSpinner.selectedItem.toString()
                nextButton.isEnabled = selectedWork != "작업을 선택해 주세요"
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // 버튼 클릭 시 동작 설정
        nextButton.setOnClickListener {
            val selectedWork = workSpinner.selectedItem.toString()

            when (selectedWork) {
                "재직 중" -> {
                    val intent = Intent(this@WorkActivity, ActiveActivity::class.java)
                    startActivity(intent)
                }
                "퇴사 예정", "구직 중" -> {
                    val dialog = OnboardingPopUp(this) // 팝업 창 생성
                    dialog.show() // 팝업 띄우기
                }
            }
        }

        // 시스템 바 패딩 적용
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

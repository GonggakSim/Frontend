package com.example.gonggaksim_frontend

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

        //데이터 리스트 설정
        val wokList = listOf("작업을 선택해 주세요","재직 중","퇴사 예정","구직 중")

        //어뎁터 설정
        val workAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, wokList)
        workAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // 스피너 어댑터 연결
        workSpinner.adapter = workAdapter

        // 버튼 활성화 로직
        val onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val workSelected = workSpinner.selectedItem != null
                nextButton.isEnabled = workSelected

                if(workSpinner.selectedItem =="재직 중"){
                    val intent = Intent(this@WorkActivity,ActiveActivity::class.java)
                    startActivity(intent)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        workSpinner.onItemSelectedListener = onItemSelectedListener

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
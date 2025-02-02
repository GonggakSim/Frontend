package com.example.gonggaksim_frontend

import android.annotation.SuppressLint
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

class Membership2Activity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_membership2)

        val ageSpinner: Spinner = findViewById(R.id.spinner_age)
        val majorSpinner: Spinner = findViewById(R.id.spinner_major)
        val yearSpinner: Spinner = findViewById(R.id.spinner_year)

        val nextButton: Button = findViewById(R.id.btn_next)

        // 데이터 리스트 설정
        val ageList = (18..30).map { it.toString() }
        val majorList = listOf("컴퓨터공학", "전자공학", "기계공학", "경영학", "영문학")
        val yearList = listOf("1학년", "2학년", "3학년", "4학년")

        // 어댑터 설정
        val ageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ageList)
        val majorAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, majorList)
        val yearAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, yearList)

        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // 스피너 어댑터 연결
        ageSpinner.adapter = ageAdapter
        majorSpinner.adapter = majorAdapter
        yearSpinner.adapter = yearAdapter

        // 버튼 활성화 로직
        val onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val ageSelected = ageSpinner.selectedItem != null
                val majorSelected = majorSpinner.selectedItem != null
                val yearSelected = yearSpinner.selectedItem != null

                nextButton.isEnabled = ageSelected && majorSelected && yearSelected
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        ageSpinner.onItemSelectedListener = onItemSelectedListener
        majorSpinner.onItemSelectedListener = onItemSelectedListener
        yearSpinner.onItemSelectedListener = onItemSelectedListener
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
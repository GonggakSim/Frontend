package com.example.gonggaksim_frontend

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModifyInformationActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    val mypageService = RetrofitClient.getRetrofit().create(mypageService::class.java)
    val ageSpinner: Spinner = findViewById(R.id.spinner_age)
    val majorSpinner: Spinner = findViewById(R.id.spinner_major)
    val yearSpinner: Spinner = findViewById(R.id.spinner_year)
    val workSpinner: Spinner = findViewById(R.id.spinner_work)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_modify_information)


        val nextButton: Button = findViewById(R.id.modifyConfirmbtn)
        val navigateToWork = Intent(this,WorkActivity::class.java)
        nextButton.setOnClickListener{
            Log.d("Membership2Activity", "Next button clicked!")
            startActivity(navigateToWork)
        }

        // 데이터 리스트 설정
        val ageList = (18..30).map { it.toString() }
        val majorList = listOf("학과를 선택해 주세요","컴퓨터공학", "전자공학", "기계공학", "경영학", "영문학")
        val yearList = listOf("학년을 선택해 주세요","1학년", "2학년", "3학년", "4학년")

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


        val nextButton2: Button = findViewById(R.id.modifyConfirmbtn)

        //데이터 리스트 설정
        val wokList = listOf("작업을 선택해 주세요","재직 중","퇴사 예정","구직 중")

        //어뎁터 설정
        val workAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, wokList)
        workAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // 스피너 어댑터 연결
        workSpinner.adapter = workAdapter

        // 버튼 활성화 로직
        val onItemSelectedListener2 = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val workSelected = workSpinner.selectedItem != null
                nextButton2.isEnabled = workSelected

                if(workSpinner.selectedItem =="재직 중"){
                    val intent = Intent(this@ModifyInformationActivity,ActiveActivity::class.java)
                    startActivity(intent)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        //workSpinner.onItemSelectedListener = onItemSelected  Listener2



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun updateUserInfo(){
        val authToken = "Bearer ACCESS_TOKEN"
        val provider = "Google"

        val updateRequest = UserModifyData(
            age = ageSpinner.selectedItem as Int,
            department = majorSpinner.selectedItem.toString(),
            grade = yearSpinner.selectedItem.toString(),
            category = listOf("디자인/예술", "IT/개발"), // 칩그룹에서 선택된것들 리스트로 빼오기
            employmentStatus = workSpinner.selectedItem.toString(),
            employCategory = "전산"
        )

        mypageService.modifyProfile(authToken, provider, updateRequest).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    Log.d("MypageFragment", "✅ 사용자 정보 업데이트 성공: ${response.body()}")
                } else {
                    Log.e("MypageFragment", "🚨 업데이트 실패: ${response.code()} - ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("MypageFragment", "❌ 네트워크 오류: ${t.message}")
            }
        })

    }

}
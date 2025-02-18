package com.example.gonggaksim_frontend

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gonggaksim_frontend.databinding.ActivityModifyInformationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModifyInformationActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private lateinit var binding: ActivityModifyInformationBinding
    val profileService = RetrofitClient.retrofit.create(mypageService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityModifyInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 데이터 리스트 설정
        val ageList = (18..30).map { it.toString() }
        val majorList = listOf("학과를 선택해 주세요", "컴퓨터공학", "전자공학", "기계공학", "경영학", "영문학")
        val yearList = listOf("학년을 선택해 주세요", "1학년", "2학년", "3학년", "4학년")
        val workList = listOf("작업을 선택해 주세요", "재직 중", "퇴사 예정", "구직 중")

        // 어댑터 설정
        val ageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ageList)
        val majorAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, majorList)
        val yearAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, yearList)
        val workAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, workList)

        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        workAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // 스피너 어댑터 연결
        binding.spinnerAgeModify.adapter = ageAdapter
        binding.spinnerMajorModify.adapter = majorAdapter
        binding.spinnerYearModify.adapter = yearAdapter
        binding.spinnerWorkModify.adapter = workAdapter

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.button.setOnClickListener{
            updateUserInfo()
        }
    }

    private fun updateUserInfo() {
        val authToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiZW1haWwiOiJkbGF0bnFsczkyMUBkYXVtLm5ldCIsImlhdCI6MTczOTU4NzcyMCwiZXhwIjoxNzQwMTkyNTIwfQ.ECvsnse9k1a9QVkm6KJA4zS3gv9JhTGou6Q8AqCcPxM"
        val provider = ""

        val updateRequest = UserModifyData(
            age = (binding.spinnerAgeModify.selectedItem as String).toInt(),
            department = binding.spinnerMajorModify.selectedItem.toString(),
            grade = binding.spinnerYearModify.selectedItem.toString(),
            category = listOf("디자인/예술", "IT/개발"), // 칩그룹에서 선택된 것들 리스트로 가져오기
            employmentStatus = binding.spinnerWorkModify.selectedItem.toString(),
            employCategory = "전산"
        )

        profileService.modifyProfile(authToken, provider, updateRequest).enqueue(object : Callback<UserResponseModify> {
            override fun onResponse(call: Call<UserResponseModify>, response: Response<UserResponseModify>) {
                if (response.isSuccessful) {
                    Log.d("MypageFragment", "✅ 사용자 정보 업데이트 성공: ${response.body()}")
                } else {
                    Log.e("MypageFragment", "🚨 업데이트 실패: ${response.code()} - ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<UserResponseModify>, t: Throwable) {
                Log.e("MypageFragment", "❌ 네트워크 오류: ${t.message}")
            }
        })
    }
}
package com.example.gonggaksim_frontend

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TermsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_terms)

        // 체크박스 및 버튼 연결
        val checkAll = findViewById<CheckBox>(R.id.checkAll)
        val checkTerms = findViewById<CheckBox>(R.id.checkTerms)
        val checkPrivacy = findViewById<CheckBox>(R.id.checkPrivacy)
        val checkPush = findViewById<CheckBox>(R.id.checkPush)
        val confirmButton = findViewById<Button>(R.id.confirmButton)

        val serviceButton = findViewById<ImageButton>(R.id.checkServiceBtn)
        val personalInformationButton = findViewById<ImageButton>(R.id.checkPrivacyBtn)
        val pushNotificationButton = findViewById<ImageButton>(R.id.checkPushBtn)

        // 서비스 이용약관 상세 페이지 이동
        serviceButton.setOnClickListener {
            startActivity(Intent(this, ServiceActivity::class.java))
        }

        // 개인정보 처리방침 상세 페이지 이동
        personalInformationButton.setOnClickListener {
            startActivity(Intent(this, PersonalInformationActivity::class.java))
        }

        // 푸시 알림 안내 상세 페이지 이동
        pushNotificationButton.setOnClickListener {
            startActivity(Intent(this, PushNotificationActivity::class.java))
        }

        // 전체 동의 체크박스 선택 시, 모든 개별 체크박스 선택 및 버튼 활성화
        checkAll.setOnCheckedChangeListener { _, isChecked ->
            checkTerms.isChecked = isChecked
            checkPrivacy.isChecked = isChecked
            checkPush.isChecked = isChecked
            confirmButton.isEnabled = isChecked && checkTerms.isChecked && checkPrivacy.isChecked
        }

        // 개별 체크박스 선택 상태 변경 시, 전체 동의 체크박스 및 버튼 활성화 여부 조정
        val checkListener = { _: Boolean ->
            confirmButton.isEnabled = checkTerms.isChecked && checkPrivacy.isChecked
            checkAll.isChecked = checkTerms.isChecked && checkPrivacy.isChecked && checkPush.isChecked
        }

        checkTerms.setOnCheckedChangeListener { _, isChecked -> checkListener(isChecked) }
        checkPrivacy.setOnCheckedChangeListener { _, isChecked -> checkListener(isChecked) }
        checkPush.setOnCheckedChangeListener { _, isChecked -> checkListener(isChecked) }

        // 이용약관 동의 후 서버에 전송
        confirmButton.setOnClickListener {
            sendAgreementStatus(checkTerms.isChecked, checkPrivacy.isChecked, checkPush.isChecked)
        }

        // 시스템 바 패딩 적용 (화면 상단, 하단 안전 영역 확보)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun sendAgreementStatus(required1: Boolean, required2: Boolean, optional: Boolean) {
        Log.d("TermsActivity", "이용약관 상태 전송 시작 - 필수1: $required1, 필수2: $required2, 선택: $optional")

        val token = getAccessToken() // 저장된 액세스 토큰 가져오기
        if (token.isEmpty()) {
            Log.e("TermsActivity", "토큰이 없습니다. 로그인이 필요합니다.")
            Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val agreements = listOf(true, true, true) // 동의 상태를 리스트로 변환
        val request = TermsAgreementRequest(agreements) // 요청 객체 생성
        val provider: String? = null

        // Retrofit을 사용하여 서버에 동의 상태 전송
        RetrofitClient.instance.agreeToTerms("Bearer $token", provider, request)
            .enqueue(object : Callback<TermsAgreementResponse> {
                override fun onResponse(
                    call: Call<TermsAgreementResponse>,
                    response: Response<TermsAgreementResponse>
                ) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        Log.d("TermsActivity", "이용약관 동의 성공")
                        navigateToNextScreen() // 성공 시 다음 화면으로 이동
                    } else {
                        Log.e("TermsActivity", "이용약관 동의 실패 - 응답 코드: ${response.code()}, 오류 내용: ${response.errorBody()?.string()}")
                        Toast.makeText(applicationContext, "이용약관 동의 실패", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<TermsAgreementResponse>, t: Throwable) {
                    Log.e("TermsActivity", "네트워크 오류: ${t.message}")
                    Toast.makeText(applicationContext, "네트워크 오류 발생", Toast.LENGTH_SHORT).show()
                }
            })

    }


    private fun navigateToNextScreen() {
        Log.d("TermsActivity", "회원 정보 입력 화면으로 이동")
        startActivity(Intent(this, Membership1Activity::class.java)) // 회원 정보 입력 화면으로 이동
        finish()
    }

    private fun getAccessToken(): String {
        val sharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("accessToken", "") ?: ""
        Log.d("TermsActivity", "저장된 액세스 토큰 가져오기: ${if (token.isNotEmpty()) "토큰 있음" else "토큰 없음"}")
        return token
    }
}

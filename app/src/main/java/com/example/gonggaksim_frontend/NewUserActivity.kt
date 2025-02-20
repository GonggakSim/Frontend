package com.example.gonggaksim_frontend

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)
        Log.d("NewUserActivity", "회원가입 화면 진입")

        val emailInput = findViewById<EditText>(R.id.new_email)
        val passwordInput = findViewById<EditText>(R.id.new_password)
        val signupButton = findViewById<Button>(R.id.nextButton)
        val backButton = findViewById<ImageView>(R.id.new_user_back_btn)

        // 뒤로가기 버튼 클릭 이벤트 추가
        backButton.setOnClickListener {
            Log.d("NewUserActivity", "뒤로가기 버튼 클릭됨")
            finish()
        }

        // 회원가입 버튼 클릭 이벤트
        signupButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                Log.d("NewUserActivity", "회원가입 버튼 클릭됨 - 이메일: $email, 비밀번호: [PROTECTED]")
                signup(email, password)
            } else {
                Toast.makeText(this, "이메일과 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                Log.w("NewUserActivity", "이메일 또는 비밀번호가 입력되지 않음")
            }
        }
    }

    private fun signup(email: String, password: String) {
        Log.d("Signup", "회원가입 요청 시작 - 이메일: $email")

        val signupRequest = SignupRequest(email, password)
        RetrofitClient.instance.signup(signupRequest).enqueue(object : Callback<SignupResponse> {
            override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { signupResponse ->
                        if (signupResponse.success) {
                            val token = signupResponse.message?.token ?: ""
                            Log.d("Signup", "회원가입 성공! 받은 토큰: $token")
                            saveToken(token)
                            navigateToMain()
                        } else {
                            Log.w("Signup", "회원가입 실패 - 서버 응답 실패: ${response.errorBody()?.string()}")
                            Toast.makeText(applicationContext, "회원가입 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Log.e("Signup", "회원가입 요청 실패 - 응답 코드: ${response.code()}, 오류: ${response.errorBody()?.string()}")
                    Toast.makeText(applicationContext, "회원가입 요청 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                Log.e("Signup", "네트워크 오류 발생 - ${t.message}")
                Toast.makeText(applicationContext, "네트워크 오류 발생", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveToken(token: String) {
        Log.d("Signup", "토큰 저장 시작 - 저장할 토큰: $token")

        val sharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("accessToken", token)
        editor.apply()

        Log.d("Signup", "토큰 저장 완료")
    }

    private fun navigateToMain() {
        Log.d("Signup", "메인 화면으로 이동")
        startActivity(Intent(this, TermsActivity::class.java))
        finish()
    }
}

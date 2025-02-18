package com.example.gonggaksim_frontend

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.d("LoginActivity", "로그인 화면 진입")

        val emailInput = findViewById<EditText>(R.id.new_email)
        val passwordInput = findViewById<EditText>(R.id.new_password)
        val loginButton = findViewById<Button>(R.id.nextButton)
        val forgotPasswordButton = findViewById<TextView>(R.id.forgottenbtn)

        // 로그인 버튼 클릭 이벤트
        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                Log.d("LoginActivity", "로그인 버튼 클릭됨 - 이메일: $email, 비밀번호: [PROTECTED]")
                login(email, password)
            } else {
                Toast.makeText(this, "이메일과 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                Log.w("LoginActivity", "이메일 또는 비밀번호가 입력되지 않음")
            }
        }

        // 비밀번호 찾기 버튼 클릭 이벤트
        forgotPasswordButton.setOnClickListener {
            Log.d("LoginActivity", "비밀번호 찾기 버튼 클릭됨")
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }

    private fun login(email: String, password: String) {
        Log.d("LoginActivity", "로그인 요청 시작 - 이메일: $email, 비밀번호 길이: ${password.length}")

        // LoginRequest 객체 생성 후 요청
        val loginRequest = LoginRequest(email, password)
        RetrofitClient.instance.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d("LoginActivity", "서버 응답 코드: ${response.code()}")

                response.body()?.let { loginResponse ->
                    Log.d("LoginActivity", "서버 응답 바디: $loginResponse") // 응답 데이터 전체 출력

                    if (loginResponse.success) {
                        val token = loginResponse.masseage?.token
                        if (token != null) {
                            Log.d("LoginActivity", "로그인 성공! 받은 토큰: $token")
                            saveToken(token)
                            navigateToMain()
                        } else {
                            Log.w("LoginActivity", "로그인 성공했지만 토큰이 없음")
                            Toast.makeText(applicationContext, "로그인 실패: 토큰이 없습니다.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.w("LoginActivity", "로그인 실패 - 서버 응답 실패: ${response.errorBody()?.string()}")
                        Toast.makeText(applicationContext, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                } ?: run {
                    Log.e("LoginActivity", "서버 응답이 null입니다.")
                    Toast.makeText(applicationContext, "서버 오류 발생", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("LoginActivity", "네트워크 오류 발생 - ${t.message}")
                Toast.makeText(applicationContext, "네트워크 오류 발생", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun saveToken(token: String?) {
        if (token.isNullOrEmpty()) {
            Log.e("LoginActivity", "저장할 토큰이 없습니다.")
            return
        }

        Log.d("LoginActivity", "토큰 저장 시작 - 저장할 토큰: $token")

        val sharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("accessToken", token)
        editor.apply()

        Log.d("LoginActivity", "토큰 저장 완료")
    }


    private fun navigateToMain() {
        Log.d("LoginActivity", "메인 화면으로 이동")
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}

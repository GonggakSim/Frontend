package com.example.gonggaksim_frontend

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.google.android.material.card.MaterialCardView

class OnboardingActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding_new)

        // 이메일 로그인 버튼
        val loginButton = findViewById<MaterialCardView>(R.id.old_user_btn)

        // 회원가입 버튼
        val signupButton = findViewById<MaterialCardView>(R.id.new_user_btn)
        // 비밀번호 찾기 버튼
        val forgottenButton = findViewById<TextView>(R.id.forgottenbtn)

        // 이메일 로그인 버튼 클릭 시 LoginActivity 이동
        loginButton.setOnClickListener {
            Log.d("Onboarding", "이메일 로그인 버튼 클릭됨")
            startActivity(Intent(this, LoginActivity::class.java))
        }

        // 회원가입 버튼 클릭 시 NewUserActivity 이동
        signupButton.setOnClickListener {
            Log.d("Onboarding", "회원가입 버튼 클릭됨")
            startActivity(Intent(this, NewUserActivity::class.java))
        }

        // 비밀번호 찾기 버튼 클릭 시 ForgotPasswordActivity 이동
        forgottenButton.setOnClickListener {
            Log.d("Onboarding", "비밀번호 찾기 버튼 클릭됨")
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }



        // 시스템 바 패딩 적용
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Null 체크 추가
        val mainView = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        } else {
            Log.e("Onboarding", "mainView가 null입니다. XML에서 id가 올바르게 설정되었는지 확인하세요.")
        }

        // QA 테스트용 - 로그인 버튼 클릭 시 TestFragment로 이동
//        loginButton.setOnClickListener {
//            Log.d("Onboarding", "QA 테스트: 로그인 버튼 클릭 시 TestFragment로 이동")
//
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
    }
}

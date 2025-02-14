package com.example.gonggaksim_frontend

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_forgot_pw)
//
//        val emailInput = findViewById<EditText>(R.id.new_email)
//        val sendEmailButton = findViewById<Button>(R.id.nextButton)
//
//        // 이메일 전송 버튼 클릭 이벤트
//        sendEmailButton.setOnClickListener {
//            val email = emailInput.text.toString().trim()
//
//            if (email.isNotEmpty()) {
//                sendPasswordResetEmail(email)
//            } else {
//                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun sendPasswordResetEmail(email: String) {
//        Log.d("ForgotPassword", "비밀번호 재설정 이메일 요청: 이메일=$email")
//
//        RetrofitClient.instance.forgotPassword(ForgotPasswordRequest(email)).enqueue(object : Callback<ForgotPasswordResponse> {
//            override fun onResponse(call: Call<ForgotPasswordResponse>, response: Response<ForgotPasswordResponse>) {
//                if (response.isSuccessful) {
//                    Toast.makeText(applicationContext, "비밀번호 재설정 이메일이 전송되었습니다.", Toast.LENGTH_SHORT).show()
//                    finish()
//                } else {
//                    Toast.makeText(applicationContext, "이메일 전송 실패", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<ForgotPasswordResponse>, t: Throwable) {
//                Toast.makeText(applicationContext, "네트워크 오류 발생", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
}

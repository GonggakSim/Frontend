package com.example.gonggaksim_frontend

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import java.security.MessageDigest
import kotlin.io.encoding.Base64
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OnboardingActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)

        val kakaoButton = findViewById<ImageButton>(R.id.kakaologin)
        val naverButton = findViewById<ImageButton>(R.id.naverlogin)
        val googleButton = findViewById<ImageButton>(R.id.googlelogin)

        //계정 세팅 화면으로 이동
        val forgottenButton = findViewById<TextView>(R.id.forgottenbtn)
        val navigateToMembershipSetting = Intent(this, MembershipSettingActivity::class.java)
        forgottenButton.setOnClickListener {
            startActivity(navigateToMembershipSetting)
        }

        // 버튼 클릭 시 약관 동의 화면으로 이동
        val navigateToTerms = Intent(this, TermsActivity::class.java)

        // 카카오 로그인 버튼 클릭 이벤트 설정
        kakaoButton.setOnClickListener {
            kakaoLogin()
        }
        naverButton.setOnClickListener {
            startActivity(navigateToTerms)
        }
        googleButton.setOnClickListener {
            startActivity(navigateToTerms)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun kakaoLogin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            // 카카오톡 로그인
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                handleLoginResult(token, error)
            }
        } else {
            // 카카오 계정 로그인
            UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                handleLoginResult(token, error)
            }
        }
    }

    // 로그인 결과 처리 함수
    private fun handleLoginResult(token: OAuthToken?, error: Throwable?) {
        if (error != null) {
            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                Log.e("KakaoLogin", "사용자가 로그인을 취소했습니다.", error)
                runOnUiThread {
                    Toast.makeText(this, "로그인이 취소되었습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.e("KakaoLogin", "로그인 실패: $error")
                runOnUiThread {
                    Toast.makeText(this, "로그인에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        } else if (token != null) {
            Log.i("KakaoLogin", "로그인 성공: ${token.accessToken}")
//
//            // 토큰 저장
//            saveToken(token)

            // 토큰을 서버로 전달
            sendTokenToServer(token.accessToken)
        }
    }

    private fun saveToken(accessToken: String, refreshToken: String?) {
        val sharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("accessToken", accessToken)
            refreshToken?.let { putString("refreshToken", it) }
            apply()
        }
        Log.i("KakaoLogin", "토큰이 SharedPreferences에 저장되었습니다.")
    }


    private fun sendTokenToServer(accessToken: String) {
        // Retrofit 호출
        val kakaoService = RetrofitClient.getRetrofit().create(ApiService::class.java)
        kakaoService.kakaoLogin(
            "Bearer $accessToken", // Bearer 형식
        ).enqueue(object : Callback<KakaoResponse> {
            override fun onResponse(call: Call<KakaoResponse>, response: Response<KakaoResponse>) {
//                val resp = response.body()!!
                Log.d("KakaoLogin", response.toString())
                if (response.isSuccessful) {
                        // 성공적으로 응답 받음
                        Log.d("KakaoLogin", "서버 응답 성공: ${response}")
//                        saveToken(response.accessToken ?: "", response.refreshToken)
//                        handleLoginSuccess(response.isNewUser)

                } else {
                    // 실패 응답 처리
                    Log.e("KakaoLogin", "서버 응답 실패: ${response.code()}, ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<KakaoResponse>, t: Throwable) {
                Log.e("KakaoLogin", "서버 요청 실패: ${t.message}")
            }
        })
    }

    // 신규 사용자 여부에 따른 화면 전환
    private fun handleLoginSuccess(isNewUser: Boolean) {
        val nextActivity = if (isNewUser) TermsActivity::class.java else MainActivity::class.java
        startActivity(Intent(this, nextActivity))
        finish()
    }

}
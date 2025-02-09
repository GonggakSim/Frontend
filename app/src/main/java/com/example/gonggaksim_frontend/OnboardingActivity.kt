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

            // 토큰 저장
            saveToken(token)

            // 기존 사용자 정보 가져오기 및 이동 코드
            fetchUserInfoAndNavigate()
        }
    }

    private fun saveToken(token: OAuthToken) {
        val sharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("accessToken", token.accessToken)
        editor.putString("refreshToken", token.refreshToken)
        editor.apply()
        Log.i("KakaoLogin", "토큰이 SharedPreferences에 저장되었습니다.")
    }

    private fun fetchUserInfoAndNavigate() {
        Log.d("KakaoLogin", "fetchUserInfoAndNavigate 호출됨") // 함수 호출 여부 확인

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("KakaoLogin", "사용자 정보 요청 실패: $error") // 실패 로그 확인
                runOnUiThread {
                    Toast.makeText(this, "사용자 정보를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            } else if (user != null) {
                Log.i("KakaoLogin", "사용자 정보 요청 성공: ${user.kakaoAccount?.email}")
                Log.d("KakaoLogin", "약관 동의 화면으로 이동 준비 중") // 이동 전 로그

                val navigateToTerms = Intent(this, TermsActivity::class.java)
                startActivity(navigateToTerms) // 약관 동의 화면으로 이동
                Log.d("KakaoLogin", "약관 동의 화면으로 이동 완료") // 이동 후 로그

                finish() // OnboardingActivity 종료
            }
        }
    }

    // 저장된 토큰을 활용해 사용자 정보를 가져오는 API 호출 - 일단 실행 X
    private fun fetchUserData() {
        val sharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
        val accessToken = sharedPreferences.getString("accessToken", null)

        if (accessToken != null) {
            RetrofitClient.instance.getUserInfo("Bearer $accessToken")
                .enqueue(object : Callback<UserResponse> {
                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        if (response.isSuccessful) {
                            Log.i("API", "사용자 정보: ${response.body()}")
                        } else {
                            Log.e("API", "API 호출 실패: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Log.e("API", "API 호출 실패: ${t.message}")
                    }
                })
        } else {
            Log.e("API", "AccessToken이 없습니다.")
        }
    }
}
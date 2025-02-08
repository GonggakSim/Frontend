package com.example.gonggaksim_frontend

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.Request
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OnboardingActivity : AppCompatActivity() {

    private lateinit var mGoogleSigninClient: GoogleSignInClient

    private val googleLoginResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val data = result.data

            try {
                val completedTask = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = completedTask.getResult(ApiException::class.java)
                val idToken = account?.idToken

                if (idToken != null) {
                    sendTokenToServer(idToken)  // 서버로 토큰 전송
                } else {
                    Toast.makeText(this, "구글 로그인 실패: ID 토큰 없음", Toast.LENGTH_SHORT).show()
                }
            } catch (e: ApiException) {
                Toast.makeText(this, "구글 로그인 실패: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)

        //계정 세팅 화면으로 이동
        val forgottenButton = findViewById<TextView>(R.id.forgottenbtn)
        val navigateToMembershipSetting = Intent(this, MembershipSettingActivity::class.java)
        forgottenButton.setOnClickListener {
            startActivity(navigateToMembershipSetting)
        }

        val kakaoButton = findViewById<ImageButton>(R.id.kakaologin)
        val naverButton = findViewById<ImageButton>(R.id.naverlogin)
        val googleButton = findViewById<ImageButton>(R.id.googlelogin)

        // 버튼 클릭 시 약관 동의 화면으로 이동
        val navigateToTerms = Intent(this, TermsActivity::class.java)

        kakaoButton.setOnClickListener {
            startActivity(navigateToTerms)
        }
        naverButton.setOnClickListener {
            startActivity(navigateToTerms)
        }
        googleButton.setOnClickListener {
            signInWithGoogle()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun signInWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1000824761596-bok6nhvq9u8u5h8i7liee80ch24bnmsb.apps.googleusercontent.com")
            .requestEmail()
            .build()

        mGoogleSigninClient = GoogleSignIn.getClient(this, gso)
        mGoogleSigninClient.signOut().addOnCompleteListener {
            Log.d("GoogleSignIn", "User signed out")
        }
        googleLoginResult.launch(mGoogleSigninClient.signInIntent)
    }

    private fun sendTokenToServer(idToken: String) {
        val authService = RetrofitClient.instance
        authService.loginWithGoogle("Bearer $idToken").enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!

                    // 토큰 저장 (SharedPreferences)
                    val sharedPref = getSharedPreferences("auth", MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("accessToken", loginResponse.accessToken)
                        putString("refreshToken", loginResponse.refreshToken)
                        apply()
                    }

                    // 기존 유저인지 확인 후 화면 전환
                    if (loginResponse.isNewUser) {
                        startActivity(Intent(this@OnboardingActivity, Membership1Activity::class.java))
                    } else {
                        startActivity(Intent(this@OnboardingActivity, MainActivity::class.java))
                    }
                    finish()
                } else {
                    Toast.makeText(this@OnboardingActivity, "로그인 실패: 서버 응답 오류", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@OnboardingActivity, "로그인 실패: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
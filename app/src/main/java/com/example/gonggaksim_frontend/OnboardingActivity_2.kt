package com.example.gonggaksim_frontend

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Paint
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OnboardingActivity : AppCompatActivity() {

    private lateinit var mGoogleSigninClient: GoogleSignInClient
    private val authService = RetrofitClient.getRetrofit().create(AuthService::class.java)

    private val googleLoginResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val data = result.data

            try {
                val completedTask = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = completedTask.getResult(ApiException::class.java)

                val idToken = account?.idToken
                if (idToken != null) {
                    Log.d("GoogleSignIn", "Received idToken: $idToken")
                    sendTokenToServer(idToken)  // 서버로 idToken 전송
                } else {
                    Log.e("GoogleSignIn", "idToken is null")
                    Toast.makeText(this, "구글 로그인 실패: ID Token 없음", Toast.LENGTH_SHORT).show()
                }

                if (idToken != null) {
                    sendTokenToServer(idToken)  // 서버로 idToken 전송
                } else {
                    Toast.makeText(this, "구글 로그인 실패: ID Token 없음", Toast.LENGTH_SHORT).show()
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
        val textView = findViewById<TextView>(R.id.forgottenbtn)
        textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        // 계정 세팅 화면으로 이동
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
            .requestIdToken("309686694249-ocubd6u9od3tfki66suo0rdf1p9u8hmu.apps.googleusercontent.com")
            .requestEmail()
            .build()

        mGoogleSigninClient = GoogleSignIn.getClient(this, gso)
        mGoogleSigninClient.signOut().addOnCompleteListener {
            Log.d("GoogleSignIn", "User signed out")
        }
        googleLoginResult.launch(mGoogleSigninClient.signInIntent)
    }
    private fun sendTokenToServer(idToken: String) {
        val tokenRequest = TokenRequest(idToken)

        val call = authService.loginWithGoogle(tokenRequest)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!
                    if (loginResponse.success) {
                        saveTokens(loginResponse.accessToken, loginResponse.refreshToken)
                        Log.w("GoogleSignIn", "액세스 토큰: ${loginResponse.accessToken}")
                        navigateToNextScreen(loginResponse.isNewUser)
                    } else {
                        Log.e("GoogleSignIn", "서버 응답 실패: ${loginResponse.message}")
                        Toast.makeText(this@OnboardingActivity, loginResponse.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("GoogleSignIn", "응답 실패: ${response.errorBody()?.string()}")
                    Toast.makeText(this@OnboardingActivity, "서버 응답 오류", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("GoogleSignIn", "네트워크 오류: ${t.message}")
                Toast.makeText(this@OnboardingActivity, "네트워크 오류 발생", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun saveTokens(accessToken: String?, refreshToken: String?) {
        val sharedPref = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("accessToken", accessToken)
            putString("refreshToken", refreshToken)
            apply()
        }
    }

    private fun navigateToNextScreen(isNewUser: Boolean) {
        val nextActivity = if (isNewUser) Membership1Activity::class.java else MainActivity::class.java
        startActivity(Intent(this, nextActivity))
        finish()
    }
}
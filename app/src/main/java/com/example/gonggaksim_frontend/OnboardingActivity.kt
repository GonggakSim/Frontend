package com.example.gonggaksim_frontend

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException



class OnboardingActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)

        // 구글 로그인 옵션 설정
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1000824761596-rs97cbs8ud0f51o8pk98m3r5tuquk2oh.apps.googleusercontent.com")
            .requestEmail()
            .build()

        // GoogleSignInClient 생성
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //계정 세팅 화면으로 이동
        val forgottenButton = findViewById<TextView>(R.id.forgottenbtn)
        val navigateToMembershipSetting = Intent(this,MembershipSettingActivity::class.java)
        forgottenButton.setOnClickListener{
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
            signIn()
//            startActivity(navigateToTerms)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // 구글 로그인 화면으로 넘어가기
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 9001)
    }

    // 구글 로그인
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 9001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account?.idToken

                Log.d("GoogleSignIn", "ID Token: $idToken")

                if (idToken != null) {
                    sendTokenToServer(idToken)
                } else {
                    Log.e("GoogleSignIn", "ID Token is null")
                }
            } catch (e: ApiException) {
                Log.w("GoogleSignIn", "Sign-in failed: ${e.statusCode}", e)
            }
        }
    }

    // 구글 로그인 토큰 주고받기
    private fun sendTokenToServer(idToken: String) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://localhost:3000/oauth2/login/kakao")
            .addHeader("Authorization", "Bearer $idToken")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("API_ERROR", "Request Failed: ${e.message}", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body()?.string()
                Log.d("API_RESPONSE", "Response: $responseBody")

                if (response.isSuccessful) {
                    try {
                        val json = JSONObject(responseBody ?: "{}")
                        val isNewUser = json.optBoolean("isNewUser", false)

                        runOnUiThread {
                            if (isNewUser) {
                                Log.d("API_RESPONSE", "New User - Redirecting to ConsentActivity")
                                startActivity(Intent(this@OnboardingActivity, Membership1Activity::class.java))
                            } else {
                                Log.d("API_RESPONSE", "Existing User - Redirecting to ConsentActivity")
                                startActivity(Intent(this@OnboardingActivity, MainActivity::class.java))
                            }
                        }

                        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
                        with(sharedPreferences.edit()) {
                            putString("accessToken", json.getString("accessToken"))
                            putString("refreshToken", json.getString("refreshToken"))
                            apply()
                        }
                    } catch (e: JSONException) {
                        Log.e("API_ERROR", "JSON Parsing Error", e)
                    }
                } else {
                    Log.e("API_ERROR", "Response Failed: ${response.code()}")
                }
            }
        })
    }


}
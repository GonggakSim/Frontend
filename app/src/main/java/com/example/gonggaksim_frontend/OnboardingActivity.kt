package com.example.gonggaksim_frontend

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.graphics.Paint
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
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException



class OnboardingActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient

    private val getResult = MutableLiveData<Intent?>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)
        val textView = findViewById<TextView>(R.id.forgottenbtn)
        textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        //  네이버 로그인 SDK 초기화 (필수)
        NaverIdLoginSDK.initialize(
            this, // Context
            "yVZysAtrk9BCLuv4b8H7", // 네이버 개발자센터에서 발급받은 Client ID
            "O_dd5yeUn5", // 네이버 개발자센터에서 발급받은 Client Secret
            "공각심" // 네이버 로그인 화면에서 표시될 앱 이름
        )


        // 구글 로그인 옵션 설정
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("process.env.PASSPORT_GOOGLE_CLIENT_ID")
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
            startNaverLogin()
            //startActivity(navigateToTerms)
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

    //네이버 로그인 화면으로 넘어가기
    private fun startNaverLogin() {
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                val accessToken = NaverIdLoginSDK.getAccessToken()
                val refreshToken = NaverIdLoginSDK.getRefreshToken()
                Log.d("NaverLogin", "AccessToken: $accessToken")
                //startActivity(navigateToTerms)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Log.e("NaverLogin", "Login Failed: $message")
            }

            override fun onError(errorCode: Int, message: String) {
                Log.e("NaverLogin", "Error: $message")
            }
        }
        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
    }
    companion object{
        fun getNaverUserInfo(accessToken: String, callback: (String?) -> Unit) {
            val url = "https://openapi.naver.com/v1/nid/me"

            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer $accessToken")
                .build()

            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("NaverLogin", "네이버 프로필 요청 실패: ${e.message}")
                    callback(null)  // 실패 시 null 반환
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseData = response.body?.string()
                    Log.d("NaverLogin", "네이버 프로필 응답: $responseData")
                    callback(responseData)  // 성공 시 응답 데이터 반환
                }
            })
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
                idToken?.let { sendTokenToServer(it) }
            } catch (e: ApiException) {
                Log.w("GoogleSignIn", "Sign-in failed", e)
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
                Log.e("API_ERROR", "Request Failed", e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    response.body?.string()?.let { responseBody ->
                        try {
                            val json = JSONObject(responseBody)
                            val isNewUser = json.getBoolean("isNewUser")

                            runOnUiThread {
                                if (isNewUser) {
                                    startActivity(Intent(this@OnboardingActivity, ActiveActivity::class.java))
                                } else {
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
                    }
                }
            }
        })
    }


}


package com.example.gonggaksim_frontend

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import android.content.Context
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
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import okhttp3.*

import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

import retrofit2.Response


class OnboardingActivity : AppCompatActivity() {

    // 파이어베이스 로그인
//    private lateinit var auth: FirebaseAuth

//    // Google 로그인을 수행하는 클라이언트 객체 (이후에 초기화)
//    private lateinit var mGoogleSigninClient: GoogleSignInClient
//    // Retrofit으로 서버와 통신할 API 서비스 객체 (AuthService 인터페이스 객체) - ID 토큰을 서버로 전송
//    private val authService = RetrofitClient.getRetrofit().create(AuthService::class.java)

//    // Google 로그인 화면을 띄운 후, 로그인 결과를 처리하는 콜백
//    private val googleLoginResult =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
//            // 로그인 성공/실패에 대한 데이터
//            val data = result.data
//
//            // 중간에 에러가 날 경우를 위한 try-catch문
//            try {
//                // 로그인 시도 후 반환된 데이터를 통해 로그인 계정 정보 받기
//                val completedTask = GoogleSignIn.getSignedInAccountFromIntent(data)
//                // 로그인 성공시 계정 정보를 가져옴
//                val account = completedTask.getResult(ApiException::class.java)
//
//                // Google 계정의 ID 토큰 가져옴
//                val idToken = account?.idToken
//                // 토큰이 있는 경우
//                if (idToken != null) {
//                    // 로그에 idToken 값 출력하고, sendTokenToServer로 토큰값 전송
//                    Log.d("GoogleSignIn", "Received idToken: $idToken")
//                    sendTokenToServer(idToken)  // 서버로 idToken 전송
//                    // 토큰이 없는 경우
//                } else {
//                    // 로그에 토큰이 없다고 출력하고, 토큰 없음 - 로그인 실패 토스트메세지 보여주기
//                    Log.e("GoogleSignIn", "idToken is null")
//                    Toast.makeText(this, "구글 로그인 실패: ID Token 없음", Toast.LENGTH_SHORT).show()
//                }
//                // 예외(에러) 발생시 로그인 실패 메세지 출력
//            } catch (e: ApiException) {
//                Toast.makeText(this, "구글 로그인 실패: ${e.message}", Toast.LENGTH_SHORT).show()
//            }
//        }


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)
        val textView = findViewById<TextView>(R.id.forgottenbtn)
        textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        //계정 세팅 화면으로 이동
        val forgottenButton = findViewById<TextView>(R.id.forgottenbtn)
        val navigateToMembershipSetting = Intent(this, MembershipSettingActivity::class.java)
        forgottenButton.setOnClickListener {
            startActivity(navigateToMembershipSetting)
        }

        // 파이어베이스 로그인
//        auth = Firebase.auth

        val kakaoButton = findViewById<ImageButton>(R.id.kakaologin)
        val naverButton = findViewById<ImageButton>(R.id.naverlogin)
        val googleButton = findViewById<ImageButton>(R.id.googlelogin)

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
            startActivity(Intent(this, LoginActivity::class.java))

//            auth.signInAnonymously()
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        val user = auth.currentUser
//                        Log.d("LoginOnboarding", user!!.uid)
//                    } else {
//                        Toast.makeText(baseContext, "Authentiaction failed.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            signInWithGoogle()

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
//
//    // Google
//    private fun signInWithGoogle() {
//        // 기본 Google 로그인 옵션 설정
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            // Google 로그인 ID 토큰 요청
//            .requestIdToken("309686694249-ocubd6u9od3tfki66suo0rdf1p9u8hmu.apps.googleusercontent.com")
//            // 로그인한 Google 계정의 이메일 요청
//            .requestEmail()
//            .build()
//
//        // Google 로그인 클라이언트 생성
//        mGoogleSigninClient = GoogleSignIn.getClient(this, gso)
//        // 로그인 되어있는 경우 로그아웃 시키고 로그아웃 했다는 로그 출력
//        mGoogleSigninClient.signOut().addOnCompleteListener {
//            Log.d("GoogleSignIn", "User signed out")
//        }
//        // Google 로그인 화면 실행, 결과를 googleLoginResult에서 처리
//        googleLoginResult.launch(mGoogleSigninClient.signInIntent)
//    }
//
//    private fun sendTokenToServer(idToken: String) {
//        // idToken을 서버에 전송하기 위한 데이터클래스
//        val tokenRequest = TokenRequest(idToken)
//
//        // Retrofit을 사용하여 서버로 로그인 요청
//        val call = authService.loginWithGoogle(tokenRequest)
//        call.enqueue(object : retrofit2.Callback<LoginResponse> {
//            override fun onResponse(call: retrofit2.Call<LoginResponse>, response: Response<LoginResponse>) {
//                // 서버 응답 성공 및 응답에 body가 있는 경우 아래 코드 실행
//                if (response.isSuccessful && response.body() != null) {
//                    val loginResponse = response.body()!!
//                    if (loginResponse.success) {
//                        // 로그인 성공시 saveToken을 호출하여 토큰 저장, 다음 화면 실행
//                        saveGoogleTokens(loginResponse.accessToken, loginResponse.refreshToken)
//                        Log.w("GoogleSignIn", "액세스 토큰: ${loginResponse.accessToken}")
//                        navigateToNextScreen(loginResponse.isNewUser)
//                    } else {
//                        // 서버 응답 실패에 대한 로그 출력
//                        Log.e("GoogleSignIn", "서버 응답 실패: ${loginResponse.message}")
//                        Toast.makeText(this@OnboardingActivity, loginResponse.message, Toast.LENGTH_SHORT).show()
//                    }
//                    // 서버 응답 실패 또는 응답 body가 없는 경우 해당 로그 출력
//                } else {
//                    Log.e("GoogleSignIn", "응답 실패: ${response.errorBody()?.string()}")
//                    Toast.makeText(this@OnboardingActivity, "서버 응답 오류", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            // 네트워크 오류에 대한 로그 출력
//            override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
//                Log.e("GoogleSignIn", "네트워크 오류: ${t.message}")
//                Toast.makeText(this@OnboardingActivity, "네트워크 오류 발생", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    // sharedPreferences로 토큰 저장
//    private fun saveGoogleTokens(accessToken: String?, refreshToken: String?) {
//        val sharedPref = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
//        with(sharedPref.edit()) {
//            putString("accessToken", accessToken)
//            putString("refreshToken", refreshToken)
//            apply()
//        }
//    }
//
//    // 로그인 성공 시 신규 사용자 여부에 따른 화면 전환
//    private fun navigateToNextScreen(isNewUser: Boolean) {
//        val nextActivity = if (isNewUser) Membership1Activity::class.java else MainActivity::class.java
//        startActivity(Intent(this, nextActivity))
//        finish()
//    }
//    //Google


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
}


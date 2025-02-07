package com.example.gonggaksim_frontend

import android.annotation.SuppressLint
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException



class OnboardingActivity : AppCompatActivity() {

    private lateinit var mGoogleSigninClient: GoogleSignInClient

    private val googleLoginResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            try {
                val completedTask = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = completedTask.getResult(ApiException::class.java)
                onLoginCompleted("${account?.id}", "${account?.idToken}")
            } catch (e: ApiException) {
                onError(Error(e))
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

    private fun onLoginCompleted(userId: String?, accessToken: String?) {
        Toast.makeText(this, "구글 로그인 성공", Toast.LENGTH_SHORT).show()
        Log.e("YMC", "userId: $userId / accessToken: $accessToken")
        val intent = Intent(this@OnboardingActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun onError(error: Error?) {
        Toast.makeText(this, "구글 로그인 실패", Toast.LENGTH_SHORT).show()
        Log.e("YMC", "구글 로그인 실패 onError / error: ${error} / error.msg: ${error?.message}")
    }
}
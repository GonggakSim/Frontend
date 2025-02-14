package com.example.gonggaksim_frontend

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


// 구글링해서 가져온 파이어베이스 로그인 코드 - 이거 바탕으로 만들어놓은 ui와 연결 필요
class LoginActivity: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        val join = findViewById<Button>(R.id.join)
        val login = findViewById<Button>(R.id.login)

        join.setOnClickListener {
            val email = findViewById<EditText>(R.id.email)
            val password = findViewById<EditText>(R.id.password)
            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "회원가입 완료", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "회원가입 실패", Toast.LENGTH_LONG).show()
                    }
                }
        }

        login.setOnClickListener {
            val email = findViewById<EditText>(R.id.email)
            val password = findViewById<EditText>(R.id.password)
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "로그인 완료", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, TermsActivity::class.java))
                    } else {
                        Toast.makeText(this, "로그인 실패", Toast.LENGTH_LONG).show()
                    }
                }
        }

        val logout = findViewById<Button>(R.id.logout)
        logout.setOnClickListener {
            Firebase.auth.signOut()
            Toast.makeText(this, "로그아웃 완료", Toast.LENGTH_LONG).show()
        }
    }

}
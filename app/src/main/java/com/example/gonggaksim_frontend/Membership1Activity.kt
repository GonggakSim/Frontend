package com.example.gonggaksim_frontend

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Membership1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_membership1)

        // EditText와 Button 연결
        val nicknameEditText = findViewById<EditText>(R.id.nicknameEditText)
        val nextButton = findViewById<Button>(R.id.nextButton)

        // 버튼 클릭 이벤트 처리
        nextButton.setOnClickListener {
            val nickname = nicknameEditText.text.toString() // 입력된 닉네임 가져오기

            // 닉네임을 다른 액티비티로 전달하며 화면 전환
            val intent = Intent(this, Membership2Activity::class.java)
            intent.putExtra("nickname", nickname) // 닉네임 데이터를 인텐트에 추가
            startActivity(intent) // 화면 전환
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
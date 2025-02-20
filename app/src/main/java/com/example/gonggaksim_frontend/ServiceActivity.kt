package com.example.gonggaksim_frontend

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_service)

        val backButton = findViewById<ImageButton>(R.id.backBtn)
        val agreeButton = findViewById<AppCompatButton>(R.id.agreeButton) // 수정된 부분

        backButton.setOnClickListener {
            finish()
        }

        // "동의" 버튼 클릭 시 결과 전달 - 수정된 부분
        agreeButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("AGREE_TERMS", true) // 이용약관 동의 체크 전달
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

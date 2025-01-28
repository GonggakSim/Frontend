package com.example.gonggaksim_frontend

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class FlashAiLoadingActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.flash_ai_loading)

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, ExamSuggestionActivity::class.java)
            finishAffinity()
            startActivity(intent)

            finish()

        }, 2000)
    }
}

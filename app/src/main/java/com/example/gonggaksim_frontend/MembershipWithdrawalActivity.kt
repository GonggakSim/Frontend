package com.example.gonggaksim_frontend

import MembershipPopUpActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MembershipWithdrawalActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_membership_withdrawal)

        val backButton = findViewById<ImageButton>(R.id.withdrawalbackBtn)
        val intent = Intent(this,MypageFragment::class.java)
        backButton.setOnClickListener{
            startActivity(intent)
            finish()
        }

        val nextButton = findViewById<Button>(R.id.withdrawalButton)
        nextButton.setOnClickListener {
            val dialog = MembershipPopUpActivity(this)
            dialog.show() // 팝업 띄우기
        }

    }


}
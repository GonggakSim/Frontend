package com.example.gonggaksim_frontend

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MembershipWithdrawalActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_membership_withdrawal)

        val nextButton: ImageButton = findViewById(R.id.withdrawalbackBtn)
        val navigatetoMyPageFragment = Intent(this,MypageFragment::class.java)
        nextButton.setOnClickListener{
            startActivity(navigatetoMyPageFragment)
            finish()
        }


        val btnWithdraw = findViewById<AppCompatButton>(R.id.withdrawalButton) // 회원탈퇴 버튼

        btnWithdraw.setOnClickListener {
            showWithdrawalDialog()
        }

    }

    private fun showWithdrawalDialog() {
        val builder = AlertDialog.Builder(this)

        // 다이얼로그 제목 (굵게 강조)
        val title = SpannableString("정말 탈퇴하시겠습니까?")
        title.setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // 다이얼로그 메시지 (일부 문구 강조)
        val message = SpannableString("회원 탈퇴 후 모든 데이터가\n삭제되며 복구가 불가능합니다.")
        message.setSpan(StyleSpan(Typeface.NORMAL), 17, message.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("탈퇴") { _, _ ->
                // 탈퇴 로직 실행 (예: 서버 요청)
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss() // 팝업 닫기
            }

        val dialog = builder.create()
        dialog.show()

        // 버튼 색상 변경 (AlertDialog 버튼 색상 조절)
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(resources.getColor(android.R.color.holo_red_dark))
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(resources.getColor(android.R.color.darker_gray))


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
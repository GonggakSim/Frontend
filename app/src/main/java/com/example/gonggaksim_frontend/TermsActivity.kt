package com.example.gonggaksim_frontend

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TermsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_terms)

        // 체크박스와 버튼 연결
        val checkAll = findViewById<CheckBox>(R.id.checkAll)
        val checkTerms = findViewById<CheckBox>(R.id.checkTerms)
        val checkPrivacy = findViewById<CheckBox>(R.id.checkPrivacy)
        val checkPush = findViewById<CheckBox>(R.id.checkPush)
        val confirmButton = findViewById<Button>(R.id.confirmButton)

        //서비스 화면 이동 버튼 연결
        val serviceButton = findViewById<ImageButton>(R.id.checkServiceBtn)
        val personalInformationButton = findViewById<ImageButton>(R.id.checkPrivacyBtn)
        val pushNotificationButton = findViewById<ImageButton>(R.id.checkPushBtn)

        //버튼 클릭 시 (서비스,개인정보,푸시) 화면으로 이동
        val navigateToService = Intent(this,ServiceActivity::class.java)
        val navigateToPersonalInformation = Intent(this,PersonalInformationActivity::class.java)
        val navigateToPushNotification = Intent(this,PushNotificationActivity::class.java)

        serviceButton.setOnClickListener{
            startActivity(navigateToService)
        }

        personalInformationButton.setOnClickListener{
            startActivity(navigateToPersonalInformation)
        }

        pushNotificationButton.setOnClickListener{
            startActivity(navigateToPushNotification)
        }

        // 전체 동의 체크박스 로직
        checkAll.setOnCheckedChangeListener { _, isChecked ->
            checkTerms.isChecked = isChecked
            checkPrivacy.isChecked = isChecked
            checkPush.isChecked = isChecked
            confirmButton.isEnabled = isChecked && checkTerms.isChecked && checkPrivacy.isChecked
        }

        // 각각의 필수 항목 체크박스 상태에 따라 버튼 활성화
        val checkListener = { _: Boolean ->
            confirmButton.isEnabled = checkTerms.isChecked && checkPrivacy.isChecked
            checkAll.isChecked = checkTerms.isChecked && checkPrivacy.isChecked && checkPush.isChecked
        }

        checkTerms.setOnCheckedChangeListener { _, isChecked -> checkListener(isChecked) }
        checkPrivacy.setOnCheckedChangeListener { _, isChecked -> checkListener(isChecked) }
        checkPush.setOnCheckedChangeListener { _, isChecked -> checkListener(isChecked) }

        // 확인 버튼 클릭 이벤트
        confirmButton.setOnClickListener {
            // 약관 동의 완료 처리
            finish() // 이전 화면으로 돌아가기
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
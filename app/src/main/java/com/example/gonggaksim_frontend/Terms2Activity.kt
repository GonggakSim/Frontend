package com.example.gonggaksim_frontend

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class Terms2Activity : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_terms2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val checkAll = view.findViewById<CheckBox>(R.id.checkAll)
        val checkTerms = view.findViewById<CheckBox>(R.id.checkTerms)
        val checkPrivacy = view.findViewById<CheckBox>(R.id.checkPrivacy)
        val checkPush = view.findViewById<CheckBox>(R.id.checkPush)
        val confirmButton = view.findViewById<Button>(R.id.confirmButton)

        val serviceButton = view.findViewById<ImageButton>(R.id.checkServiceBtn)
        val personalInformationButton = view.findViewById<ImageButton>(R.id.checkPrivacyBtn)
        val pushNotificationButton = view.findViewById<ImageButton>(R.id.checkPushBtn)

        // 버튼 클릭 시 새 액티비티 열기
        serviceButton.setOnClickListener {
            startActivity(Intent(requireContext(), ServiceActivity::class.java))
        }
        personalInformationButton.setOnClickListener {
            startActivity(Intent(requireContext(), PersonalInformationActivity::class.java))
        }
        pushNotificationButton.setOnClickListener {
            startActivity(Intent(requireContext(), PushNotificationActivity::class.java))
        }

        confirmButton.setOnClickListener {
            startActivity(Intent(requireContext(), Membership1Activity::class.java))
            dismiss() // 다이얼로그 닫기
        }

        checkAll.setOnCheckedChangeListener { _, isChecked ->
            checkTerms.isChecked = isChecked
            checkPrivacy.isChecked = isChecked
            checkPush.isChecked = isChecked
            confirmButton.isEnabled = checkTerms.isChecked && checkPrivacy.isChecked
        }

        val checkListener = { _: Boolean ->
            confirmButton.isEnabled = checkTerms.isChecked && checkPrivacy.isChecked
            checkAll.isChecked = checkTerms.isChecked && checkPrivacy.isChecked && checkPush.isChecked
        }

        checkTerms.setOnCheckedChangeListener { _, isChecked -> checkListener(isChecked) }
        checkPrivacy.setOnCheckedChangeListener { _, isChecked -> checkListener(isChecked) }
        checkPush.setOnCheckedChangeListener { _, isChecked -> checkListener(isChecked) }
    }
}

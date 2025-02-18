package com.example.gonggaksim_frontend

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.RadioButton
import com.example.gonggaksim_frontend.databinding.TestNotiMultiBinding
import com.example.gonggaksim_frontend.databinding.TestNotiOxCorrectBinding
import com.example.gonggaksim_frontend.databinding.TestNotiOxIncorrectBinding

class MultiDialog(context: Context) : Dialog(context) {

    private var _binding: TestNotiMultiBinding? = null
    private val binding get() = _binding!!

    // 정답 번호 (예: 2번이 정답)
    private val correctAnswer = 2

    init {
        _binding = TestNotiMultiBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root) // 다이얼로그 레이아웃 설정

        window?.setBackgroundDrawableResource(android.R.color.transparent) // 배경 투명 설정

        // 버튼 클릭 시 정답 확인 및 레이아웃 변경
        binding.buttonX.setOnClickListener {
            checkAnswer()
        }
    }

    // 정답 확인 로직
    private fun checkAnswer() {
        val selectedId = binding.radioGroup.checkedRadioButtonId // 선택된 라디오 버튼 ID 가져오기

        if (selectedId != -1) {
            val selectedRadioButton = findViewById<RadioButton>(selectedId)
            val selectedValue = selectedRadioButton.tag.toString().toInt() // 정수 변환
            Log.d("radiovalue",selectedValue.toString())
            if (selectedValue == correctAnswer) {
                changeLayoutToCorrect()
            } else {
                shakeDialogWithBackgroundChange()
                changeLayoutToIncorrect()
            }
        } else {
            shakeDialogWithBackgroundChange()
        }
    }

    // 다이얼로그 흔들기 애니메이션
    private fun shakeDialogWithBackgroundChange() {
        val dialogView = window?.decorView // 다이얼로그의 루트 뷰 가져오기
        dialogView?.let {
            val animator = ObjectAnimator.ofFloat(it, "translationX", 0f, 25f, -25f, 15f, -15f, 6f, -6f, 0f)
            animator.duration = 500 // 애니메이션 지속 시간
            animator.start()
        }
    }

    // 오답 레이아웃으로 변경
    private fun changeLayoutToIncorrect() {
        val incorrectBinding = TestNotiOxIncorrectBinding.inflate(LayoutInflater.from(context))
        setContentView(incorrectBinding.root)
    }

    // 정답 레이아웃으로 변경
    private fun changeLayoutToCorrect() {
        val correctBinding = TestNotiOxCorrectBinding.inflate(LayoutInflater.from(context))
        setContentView(correctBinding.root)
    }

    override fun dismiss() {
        super.dismiss()
        _binding = null // 메모리 누수 방지
    }
}
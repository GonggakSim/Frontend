package com.example.gonggaksim_frontend


import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import com.example.gonggaksim_frontend.databinding.TestNotiDictateBinding
import com.example.gonggaksim_frontend.databinding.TestNotiMultiBinding
import com.example.gonggaksim_frontend.databinding.TestNotiOxCorrectBinding
import com.example.gonggaksim_frontend.databinding.TestNotiOxIncorrectBinding

class DictateDialog(context: Context) : Dialog(context) {

    private var _binding: TestNotiDictateBinding? = null
    private val binding get() = _binding!!
    private lateinit var quizData: QuizData

    init {
        _binding = TestNotiDictateBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root) // 다이얼로그 레이아웃 설정


        window?.setBackgroundDrawableResource(android.R.color.transparent) // 배경 투명 설정

        // 버튼 클릭 시 정답 확인 및 레이아웃 변경
        binding.buttonO.setOnClickListener {
            checkAnswer()
        }
    }

    fun setQuizData(quizData: QuizData) {
        this.quizData = quizData
        setupUI()
    }

    private fun setupUI() {

        binding.quizAnswer.text = quizData.question
        binding.testname.text = quizData.certificationName

    }
    // 정답 확인 로직
    private fun normalizeText(text: String): String {
        return text.trim() // 앞뒤 공백 제거
            .replace("\\s+".toRegex(), " ") // 여러 공백을 하나로
            .replace("[^\\p{L}\\p{N}\\s]".toRegex(), "") // 특수문자 제거
            .lowercase() // 대소문자 통일
    }

    private fun checkAnswer() {
        val userInput = normalizeText(binding.dictText.editableText.toString())
        val correctAnswer = normalizeText(quizData.question)

        if (userInput.contentEquals(correctAnswer)) {
            dismiss()
        } else {
            shakeDialogWithBackgroundChange()
            binding.incorrectText.text = "으앗! 틀렸어요, 다시 해볼까요?"
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


    override fun dismiss() {
        super.dismiss()
        _binding = null // 메모리 누수 방지
    }
}
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import com.example.gonggaksim_frontend.AdviceQuotes
import com.example.gonggaksim_frontend.QuizData
import com.example.gonggaksim_frontend.R
import com.example.gonggaksim_frontend.databinding.TestNotiMultiBinding
import com.example.gonggaksim_frontend.databinding.TestNotiOxCorrectBinding
import com.example.gonggaksim_frontend.databinding.TestNotiOxIncorrectBinding
import kotlin.random.Random

class MultiDialog(context: Context) : Dialog(context) {

    private var _binding: TestNotiMultiBinding? = null
    private val binding get() = _binding!!
    private lateinit var quizData: QuizData

    private lateinit var timer: CountDownTimer
    private val totalTime = 15000L // 15초 타이머
    private val interval = 1000L // 1초 간격



    init {
        _binding = TestNotiMultiBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        window?.setBackgroundDrawableResource(android.R.color.transparent) // 배경 투명 설정

        // 타이머 시작
        setupTimer()

        // 버튼 클릭 시 정답 확인
        binding.buttonConfirm.setOnClickListener {
            checkAnswer()
        }
    }
    private fun getRandomAdvice(): String {
        return AdviceQuotes.adviceList.random()  // 리스트에서 랜덤으로 조언 선택
    }

    fun setQuizData(quizData: QuizData) {
        this.quizData = quizData
        setupUI()
    }

    private fun setupUI() {
        val radioGroupOptions = binding.radioGroup

        quizData.options.forEachIndexed { index, optionText ->
            val radioButton = radioGroupOptions.getChildAt(index) as? RadioButton
            radioButton?.text = optionText
            radioButton?.tag = index + 1 // 정답 매핑용 태그
        }

        binding.quizAnswer.text = quizData.question
        binding.testname.text = quizData.certificationName

        // 타이머 ProgressBar 초기화
        binding.timerBar.max = (totalTime / interval).toInt()
        binding.timerBar.progress = binding.timerBar.max
    }

    /**
     * 타이머 설정
     */
    private fun setupTimer() {
        timer = object : CountDownTimer(totalTime, interval) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = (millisUntilFinished / interval).toInt()
                binding.timerBar.progress = secondsLeft
                binding.timerText.text = "${secondsLeft}"
            }

            override fun onFinish() {
                // 시간 초과 시 오답 처리
                changeLayoutToIncorrect()
            }
        }

        timer.start()
    }

    /**
     * 정답 확인 로직
     */
    private fun checkAnswer() {
        val selectedId = binding.radioGroup.checkedRadioButtonId

        if (selectedId != -1) {
            val selectedRadioButton = findViewById<RadioButton>(selectedId)
            val selectedValue = selectedRadioButton.tag.toString().toInt()

            Log.d("radiovalue", selectedValue.toString())

            if (selectedValue.toString() == quizData.answer) {
                timer.cancel() // 정답 시 타이머 취소
                changeLayoutToCorrect()
            } else {
                shakeDialogWithBackgroundChange()
                changeLayoutToIncorrect()
            }
        } else {
            shakeDialogWithBackgroundChange()
        }
    }

    /**
     * 다이얼로그 흔들기 애니메이션
     */
    private fun shakeDialogWithBackgroundChange() {
        val dialogView = window?.decorView
        dialogView?.let {
            val animator = ObjectAnimator.ofFloat(it, "translationX", 0f, 25f, -25f, 15f, -15f, 6f, -6f, 0f)
            animator.duration = 500
            animator.start()
        }
    }

    /**
     * 오답 레이아웃으로 변경
     */
    private fun changeLayoutToIncorrect() {
        val incorrectBinding = TestNotiOxIncorrectBinding.inflate(LayoutInflater.from(context))
        setContentView(incorrectBinding.root)
        val quizAnswerTextView = findViewById<TextView>(R.id.quiz_answer)
        quizAnswerTextView.text = "정답 : ${quizData.answer}"
        val adviceTextView = findViewById<TextView>(R.id.quiz_advice)
        adviceTextView.text = getRandomAdvice()
        val confirmBtn = findViewById<Button>(R.id.buttonO)
        confirmBtn.setOnClickListener{
            dismiss()
        }

    }

    private fun changeLayoutToCorrect() {
        val correctBinding = TestNotiOxCorrectBinding.inflate(LayoutInflater.from(context))
        setContentView(correctBinding.root)
        val quizAnswerTextView = findViewById<TextView>(R.id.quiz_answer)
        quizAnswerTextView.text = "정답 : ${quizData.answer}"
        val confirmBtn = findViewById<Button>(R.id.buttonO)
        confirmBtn.setOnClickListener{
            dismiss()
        }
    }

    override fun dismiss() {
        super.dismiss()
        timer.cancel() // 다이얼로그 닫힐 때 타이머 취소
        _binding = null // 메모리 누수 방지
    }
}
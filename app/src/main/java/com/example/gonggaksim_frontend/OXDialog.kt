import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import com.example.gonggaksim_frontend.AdviceQuotes
import com.example.gonggaksim_frontend.QuizData
import com.example.gonggaksim_frontend.R
import com.example.gonggaksim_frontend.databinding.TestNotiMultiBinding
import com.example.gonggaksim_frontend.databinding.TestNotiOxBinding
import kotlin.random.Random

class OxDialog(context: Context) : Dialog(context) {
    private lateinit var quizData : QuizData
    private var _binding: TestNotiOxBinding? = null
    private val binding get() = _binding!!

    private lateinit var timer: CountDownTimer
    private val totalTime = 15000L // 15초 타이머
    private val interval = 1000L // 1초 간격

    init {
        _binding = TestNotiOxBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root) // 다이얼로그 레이아웃 설정
        window?.setBackgroundDrawableResource(android.R.color.transparent) // 배경 투명 설정
        setupTimer()
        // 버튼 클릭 시 애니메이션 및 배경 변경 실행
        val buttonO = findViewById<View>(R.id.buttonO)
        val buttonX = findViewById<View>(R.id.buttonConfirm)

        buttonO.setOnClickListener {
            checkAnswer(userAnswer = "true")
        }

        // X 버튼 클릭 이벤트
        buttonX.setOnClickListener {
            checkAnswer(userAnswer = "false")
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
        binding.quizAnswer.text = quizData.question
        binding.testname.text = quizData.certificationName

    }

    // 다이얼로그 흔들기와 동시에 배경 변경
    private fun shakeDialogWithBackgroundChange() {
        val dialogView = window?.decorView // 다이얼로그의 루트 뷰 가져오기
        dialogView?.let {
            // 1. 흔들리는 애니메이션 추가
            val animator = ObjectAnimator.ofFloat(it, "translationX", 0f, 25f, -25f, 15f, -15f, 6f, -6f, 0f)
            animator.duration = 500 // 애니메이션 지속 시간

            animator.start()
        }
    }
    private fun changeLayoutToIncorrect() {
        // 현재 다이얼로그에 새로운 레이아웃 설정
        shakeDialogWithBackgroundChange()
        setContentView(R.layout.test_noti_ox_incorrect)
        val quizAnswerTextView = findViewById<TextView>(R.id.quiz_answer)
        if (quizData.answer == "true")
        {
            quizAnswerTextView.setCompoundDrawablesWithIntrinsicBounds(
                0,0, R.drawable.icon_correct,0
            )
        }
        else {
            quizAnswerTextView.setCompoundDrawablesWithIntrinsicBounds(
                0,0, R.drawable.icon_incorrect,0
            )
        }
        val adviceTextView = findViewById<TextView>(R.id.quiz_advice)
        adviceTextView.text = getRandomAdvice()
        val confirmBtn = findViewById<Button>(R.id.buttonO)
        confirmBtn.setOnClickListener{
            dismiss()
        }

    }
    private fun changeLayoutToCorrect() {
        // 현재 다이얼로그에 새로운 레이아웃 설정
        setContentView(R.layout.test_noti_ox_correct)
        val quizAnswerTextView = findViewById<TextView>(R.id.quiz_answer)
        if (quizData.answer == "true")
        {
            quizAnswerTextView.setCompoundDrawablesWithIntrinsicBounds(
                0,0, R.drawable.icon_correct,0
            )
        }
        else {
            quizAnswerTextView.setCompoundDrawablesWithIntrinsicBounds(
                0,0, R.drawable.icon_incorrect,0
            )
        }
        val confirmBtn = findViewById<Button>(R.id.buttonO)
        confirmBtn.setOnClickListener{
            dismiss()
        }
    }
    private fun checkAnswer(userAnswer: String) {
        val correctAnswer = quizData.answer.trim().lowercase() // 정답 값 (true/false)

        if (userAnswer == correctAnswer) {
            // 정답
            changeLayoutToCorrect()
        } else {
            // 오답
            changeLayoutToIncorrect()
        }
    }
    private fun setupTimer() {
        timer = object : CountDownTimer(totalTime, interval) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = (millisUntilFinished / interval).toInt()
                binding.timerBar2.progress = secondsLeft
                binding.timerText2.text = "${secondsLeft}"
            }

            override fun onFinish() {
                // 시간 초과 시 오답 처리
                changeLayoutToIncorrect()
            }
        }

        timer.start()
    }
    override fun dismiss() {
        super.dismiss()
        timer.cancel() // 다이얼로그 닫힐 때 타이머 취소
        _binding = null // 메모리 누수 방지
    }

}
import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.example.gonggaksim_frontend.R

class OxDialog(context: Context) : Dialog(context) {

    init {
        setContentView(R.layout.test_noti_ox) // 다이얼로그 레이아웃 설정
        window?.setBackgroundDrawableResource(android.R.color.transparent) // 배경 투명 설정

        // 버튼 클릭 시 애니메이션 및 배경 변경 실행
        val buttonO = findViewById<View>(R.id.buttonO)
        val buttonX = findViewById<View>(R.id.buttonX)

        buttonO?.setOnClickListener {
            changeLayoutToIncorrect()
            shakeDialogWithBackgroundChange()
        }
        buttonX?.setOnClickListener {
            changeLayoutToCorrect()
        }

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
        setContentView(R.layout.test_noti_ox_incorrect)
    }
    private fun changeLayoutToCorrect() {
        // 현재 다이얼로그에 새로운 레이아웃 설정
        setContentView(R.layout.test_noti_ox_correct)
    }


}
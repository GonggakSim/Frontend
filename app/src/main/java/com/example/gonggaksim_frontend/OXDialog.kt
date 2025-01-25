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
        val buttonX = findViewById<View>(R.id.buttonX)
        buttonX?.setOnClickListener {
            shakeDialogWithBackgroundChange()
        }
    }

    // 다이얼로그 흔들기와 동시에 배경 변경
    private fun shakeDialogWithBackgroundChange() {
        val dialogView = window?.decorView // 다이얼로그의 루트 뷰 가져오기
        val linearLayout = findViewById<LinearLayout>(R.id.OXLayout) // 레이아웃 ID 가져오기

        dialogView?.let {
            // 1. 흔들리는 애니메이션 추가
            val animator = ObjectAnimator.ofFloat(it, "translationX", 0f, 25f, -25f, 15f, -15f, 6f, -6f, 0f)
            animator.duration = 500 // 애니메이션 지속 시간

            animator.addListener(object : android.animation.AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    // 애니메이션 시작 시 배경 변경
                    linearLayout?.setBackgroundResource(R.drawable.test_noti_design_incorrect)
                }
            })

            animator.start()
        }
    }
}
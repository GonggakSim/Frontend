package com.example.gonggaksim_frontend

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Button

open class BaseDialog(context: Context) : Dialog(context) {
    init {
        setCancelable(true)
    }

    private fun shakeAnimation(view: View) {
        val animator = ObjectAnimator.ofFloat(view, "translationX", 0f, 25f, -25f, 15f, -15f, 6f, -6f, 0f)
        animator.duration = 500 // 애니메이션 지속 시간
        animator.start()
    }
}

class DictateDialog(context: Context) : BaseDialog(context) {
    init {
        setContentView(R.layout.test_noti_dictate)
    }
}

class MultiDialog(context: Context) : BaseDialog(context) {
    init {
        setContentView(R.layout.test_noti_multi)
    }
}

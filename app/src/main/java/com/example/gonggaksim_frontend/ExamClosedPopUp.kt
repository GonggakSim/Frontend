package com.example.gonggaksim_frontend

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.gonggaksim_frontend.databinding.FragmentExamClosedPopupBinding

class ExamClosedPopUp(
    context: Context,
) : Dialog(context) {

    private lateinit var binding: FragmentExamClosedPopupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentExamClosedPopupBinding.inflate(layoutInflater)
        setContentView(binding.root) // 수정: inflate한 뷰로 설정
        window?.setBackgroundDrawableResource(android.R.color.transparent)

        binding.close.setOnClickListener {
            dismiss()
        }
    }
}



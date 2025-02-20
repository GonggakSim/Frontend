package com.example.gonggaksim_frontend

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.example.gonggaksim_frontend.databinding.TestNotiOnlynotiBinding
import com.example.gonggaksim_frontend.databinding.TestNotiOxBinding

class NotiOnlyDialog(context: Context) : Dialog(context) {
    private var _binding: TestNotiOnlynotiBinding? = null
    private val binding get() = _binding!!

    init{
        _binding = TestNotiOnlynotiBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root) // 다이얼로그 레이아웃 설정
        window?.setBackgroundDrawableResource(android.R.color.transparent) // 배경 투명 설정

        binding.buttonConfirm.setOnClickListener{
            dismiss()
        }
    }

}
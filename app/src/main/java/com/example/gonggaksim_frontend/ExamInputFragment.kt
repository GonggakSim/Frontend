package com.example.gonggaksim_frontend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gonggaksim_frontend.databinding.FragmentExamInputBinding

class ExamInputFragment : Fragment() {
    private lateinit var binding: FragmentExamInputBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExamInputBinding.inflate(inflater, container, false)

        clickButtonEvent()

        return binding.root
    }

    private fun clickButtonEvent() {
        binding.examDateEt.setOnClickListener {
            val modalBottomSheet = ModalBottomSheet.newInstance()
            modalBottomSheet.show(parentFragmentManager, "ModalBottomSheet")
        }

//        binding.reminderBtnOn.setOnClickListener {
//            binding.reminderBtnOn.visibility = View.GONE
//            binding.reminderBtnOff.visibility = View.VISIBLE
//        }
//        binding.reminderBtnOff.setOnClickListener {
//            binding.reminderBtnOn.visibility = View.VISIBLE
//            binding.reminderBtnOff.visibility = View.GONE
//        }
//        binding.reminderBackground.setOnClickListener {
//            if(binding.reminderBtnOn.visibility == View.GONE) {
//                binding.reminderBtnOn.visibility = View.VISIBLE
//                binding.reminderBtnOff.visibility = View.GONE
//            }
//            else {
//                binding.reminderBtnOn.visibility = View.GONE
//                binding.reminderBtnOff.visibility = View.VISIBLE
//            }
//        }

        binding.esfBtn.setOnClickListener {

        }
    }
}
package com.example.gonggaksim_frontend

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
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
        editTextEvent()

        return binding.root
    }

    private fun clickButtonEvent() {
        binding.examDateEt.setOnClickListener {
            val modalBottomSheet = ModalBottomSheet.newInstance()
            modalBottomSheet.show(parentFragmentManager, "ModalBottomSheet")
        }

        binding.examNameBtn.setOnClickListener {

        }
        binding.examDateBtn.setOnClickListener {

        }

        binding.esfBtn.setOnClickListener {
            val fragmentCalendar = CalenderFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragmentCalendar)
                .commit()
        }
    }
    private fun editTextEvent() {
        binding.examNameEt.addTextChangedListener {
            val text = binding.examNameEt.text.toString()
            if(text.isEmpty()) {
                binding.examNameBtn.visibility = View.GONE
            } else {
                binding.examNameBtn.visibility = View.VISIBLE
            }
        }

        binding.examDateEt.addTextChangedListener {
            val text = binding.examDateEt.text.toString()
            if(text.isEmpty()) {
                binding.examDateBtn.visibility = View.GONE
            } else {
                binding.examDateBtn.visibility = View.VISIBLE
            }
        }
    }
}
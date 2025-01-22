package com.example.gonggaksim_frontend

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gonggaksim_frontend.databinding.FragmentCalenderBinding

class CalenderFragment : Fragment() {

    private var _binding: FragmentCalenderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCalenderBinding.inflate(inflater, container, false)

        binding.plsBtn.setOnClickListener {
            val fragmentExamInput = ExamInputFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragmentExamInput)
                .commit()
        }

        // 액티비티 테스트용
//        binding.plsBtn.setOnClickListener {
            //시험일정추천(점수)
//            startActivity(Intent(requireContext(), ExamDivPointActivity()::class.java))
//
            //시험일정추천(점수x)
//            startActivity(Intent(requireContext(), ExamDivSuccfailActivity()::class.java))
//
            //ai 분석
//            startActivity(Intent(requireContext(), ExamSuggestionActivity()::class.java))
//        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
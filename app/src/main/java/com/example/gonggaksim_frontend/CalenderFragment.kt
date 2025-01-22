package com.example.gonggaksim_frontend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gonggaksim_frontend.databinding.FragmentCalenderBinding

class CalenderFragment : Fragment() {

    private lateinit var binding: FragmentCalenderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCalenderBinding.inflate(inflater, container, false)

        binding.plsBtn.setOnClickListener {
            val fragmentExamInput = ExamInputFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragmentExamInput)
                .commit()
        }

        return binding.root


    }
}
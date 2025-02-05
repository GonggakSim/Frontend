package com.example.gonggaksim_frontend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.gonggaksim_frontend.databinding.FragmentCalenderBinding
import java.time.LocalDate

class CalenderFragment : Fragment() {

    private var _binding: FragmentCalenderBinding? = null
    private val binding get() = _binding!!

    private lateinit var monthAdapter: MonthAdapter

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

        setListener()

        return binding.root
    }

    fun setListener() {
        initCalendar()
    }

    fun initCalendar(){
        var date = arrayListOf<String>("2025년 02월 02일")

        monthAdapter = MonthAdapter(this, date)
        binding.customCalendar.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter = monthAdapter
            scrollToPosition(Int.MAX_VALUE/2)
        }
        val snap = PagerSnapHelper()
        if(binding.customCalendar.onFlingListener == null){
            snap.attachToRecyclerView(binding.customCalendar)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
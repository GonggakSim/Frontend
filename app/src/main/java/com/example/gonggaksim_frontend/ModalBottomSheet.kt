package com.example.gonggaksim_frontend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.gonggaksim_frontend.databinding.ModalBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ModalBottomSheet : BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var monthAdapter2: MonthAdapter2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ModalBottomSheetBinding.inflate(inflater, container, false)

        binding.mbsBtn.setOnClickListener {
            dismiss()
        }

        setListener()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): ModalBottomSheet {
            return ModalBottomSheet()
        }
    }

    fun setListener() {
        initCalendar()
    }

    fun initCalendar(){
        var date = arrayListOf<String>("2025년 01월 01일","2025년 01월 28일","2025년 01월 09일","2025년 01월 18일")

        monthAdapter2 = MonthAdapter2(this, date)
        binding.mdsCalendar.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter = monthAdapter2
            scrollToPosition(Int.MAX_VALUE/2)
        }
        val snap = PagerSnapHelper()
        if(binding.mdsCalendar.onFlingListener == null){
            snap.attachToRecyclerView(binding.mdsCalendar)
        }
    }
}
package com.example.gonggaksim_frontend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.gonggaksim_frontend.databinding.ModalBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar
import java.util.Date

class ModalBottomSheet : BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var monthAdapter2: MonthAdapter2

    var onDateRangeSelected: ((String) -> Unit)? = null
    lateinit var dateRange: String

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
        monthAdapter2 = MonthAdapter2(this, DateEvent().date) { startDate, endDate ->
            updateSelectedDateRange(startDate, endDate)
        }
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

    private fun updateSelectedDateRange(startDate: Date?, endDate: Date?) {
        if (startDate != null) {
            if (endDate != null) {
                dateRange = "${formatDate(startDate)}~${formatDate(endDate)}"
            }
            // 콜백 호출로 선택한 날짜 전달
            onDateRangeSelected?.invoke(dateRange)
        }
    }

    private fun formatDate(date: Date): String {
        val calendar = Calendar.getInstance().apply { time = date }
        val year = calendar.get(Calendar.YEAR)
        val month = String.format("%02d", calendar.get(Calendar.MONTH) + 1)
        val day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))
        return "$year.$month.$day"
    }
}
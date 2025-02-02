package com.example.gonggaksim_frontend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gonggaksim_frontend.databinding.FragmentExamScheduleBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ExamScheduleFragment : Fragment() {

    private var _binding: FragmentExamScheduleBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ExamScheduleAdapter // 어댑터

    private val examSchedules = mapOf(
        "1월" to listOf("1일\n09:00", "2일\n10:00", "3일\n11:00", "4일\n12:00", "5일\n13:00", "6일\n14:00", "9일\n11:40", "10일\n12:50", "11일\n13:30", "12일\n14:20"),
        "2월" to listOf("7일\n09:10", "8일\n10:10", "9일\n11:10", "10일\n12:10", "11일\n13:10", "12일\n14:10"),
        "3월" to listOf("13일\n09:20", "14일\n10:20", "15일\n11:20", "16일\n12:20", "17일\n13:20", "18일\n14:20", "9일\n11:40", "10일\n12:50", "11일\n13:30", "12일\n14:20"),
        "4월" to listOf("19일\n09:30", "20일\n10:30", "21일\n11:30", "22일\n12:30", "23일\n13:30", "24일\n14:30"),
        "5월" to listOf("25일\n09:40", "26일\n10:40", "27일\n11:40", "28일\n12:40", "29일\n13:40", "30일\n14:40", "9일\n11:40", "10일\n12:50", "11일\n13:30", "12일\n14:20"),
        "6월" to listOf("1일\n09:50", "2일\n10:50", "3일\n11:50", "4일\n12:50", "5일\n13:50", "6일\n14:50"),
        "7월" to listOf("7일\n09:00", "8일\n10:00", "9일\n11:00", "10일\n12:00", "11일\n13:00", "12일\n14:00", "9일\n11:40", "10일\n12:50", "11일\n13:30", "12일\n14:20"),
        "8월" to listOf("13일\n09:10", "14일\n10:10", "15일\n11:10", "16일\n12:10", "17일\n13:10", "18일\n14:10"),
        "9월" to listOf("19일\n09:20", "20일\n10:20", "21일\n11:20", "22일\n12:20", "23일\n13:20", "24일\n14:20", "9일\n11:40", "10일\n12:50", "11일\n13:30", "12일\n14:20"),
        "10월" to listOf("25일\n09:30", "26일\n10:30", "27일\n11:30", "28일\n12:30", "29일\n13:30", "30일\n14:30"),
        "11월" to listOf("8일\n09:20", "8일\n10:30", "9일\n11:40", "10일\n12:50", "11일\n13:30", "12일\n14:20", "9일\n11:40", "10일\n12:50", "11일\n13:30", "12일\n14:20"),
        "12월" to listOf("1일\n09:00", "2일\n10:00", "3일\n11:00", "4일\n12:00", "5일\n13:00", "6일\n14:00")
    )

    private var currentMonth: String = "11월" // 초기 월 설정

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExamScheduleBinding.inflate(inflater, container, false)

        setupRecyclerView() // RecyclerView 설정
        setupMonthNavigation() // 월 이동 버튼 설정
        setupRegisterButton() // 접수하기 버튼 설정
        setupAddToScheduleButton() // 일정 추가하기 버튼 설정

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = ExamScheduleAdapter { isAnyButtonSelected ->
            onScheduleButtonSelectionChanged(isAnyButtonSelected)
        }
        binding.scheduleRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.scheduleRecyclerView.adapter = adapter

        updateScheduleForMonth(currentMonth) // 초기 데이터 설정
    }

    private fun setupMonthNavigation() {
        binding.btnPreviousMonth.setOnClickListener {
            val previousMonth = getPreviousMonth(currentMonth)
            if (previousMonth != null) {
                updateScheduleForMonth(previousMonth)
            }
        }

        binding.btnNextMonth.setOnClickListener {
            val nextMonth = getNextMonth(currentMonth)
            if (nextMonth != null) {
                updateScheduleForMonth(nextMonth)
            }
        }
    }

    private fun getPreviousMonth(current: String): String? {
        val months = examSchedules.keys.toList()
        val currentIndex = months.indexOf(current)
        return if (currentIndex > 0) months[currentIndex - 1] else months.last()
    }

    private fun getNextMonth(current: String): String? {
        val months = examSchedules.keys.toList()
        val currentIndex = months.indexOf(current)
        return if (currentIndex < months.size - 1) months[currentIndex + 1] else months.first()
    }

    private fun updateScheduleForMonth(month: String) {
        currentMonth = month
        binding.currentMonth.text = month // 현재 월 텍스트 갱신
        val scheduleList = examSchedules[month] ?: emptyList()
        adapter.submitList(scheduleList)
    }

    private fun setupRegisterButton() {
        binding.btnRegister.isEnabled = false // 기본 비활성화
        binding.btnRegister.setOnClickListener {
            val selectedButtonText = adapter.getSelectedButtonText()
            if (selectedButtonText != null) {
                showConfirmationPopup(selectedButtonText)
            }
        }
    }

    private fun setupAddToScheduleButton() {
        binding.btnAddToSchedule.visibility = View.GONE
        binding.btnAddToSchedule.setOnClickListener {
            showAddToSchedulePopup()
        }
    }

    private fun onScheduleButtonSelectionChanged(isAnyButtonSelected: Boolean) {
        // 접수하기 버튼 상태 업데이트
        binding.btnRegister.isEnabled = isAnyButtonSelected
        binding.btnRegister.backgroundTintList = requireContext().getColorStateList(
            if (isAnyButtonSelected) R.color.main_01 else R.color.grayscale_06
        )
        binding.btnAddToSchedule.visibility = if (isAnyButtonSelected) View.VISIBLE else View.GONE
    }

    private fun showConfirmationPopup(selectedText: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("선택 확인")
            .setMessage("$selectedText 일정이 선택되었습니다.")
            .setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun showAddToSchedulePopup() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_add_to_schedule_popup, null)

        val btnYes = dialogView.findViewById<Button>(R.id.btn_yes)
        val btnNo = dialogView.findViewById<Button>(R.id.btn_no)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()

        btnYes.setOnClickListener {
            Toast.makeText(requireContext(), "일정이 추가되었습니다.", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        btnNo.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

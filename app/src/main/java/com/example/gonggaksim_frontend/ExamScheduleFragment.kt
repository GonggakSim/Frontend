package com.example.gonggaksim_frontend

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gonggaksim_frontend.databinding.FragmentExamScheduleBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExamScheduleFragment : Fragment() {


    private var _binding: FragmentExamScheduleBinding? = null
    private val binding get() = _binding!!

    private var certificationName: String = ""
    //private var binding: FragmentExamScheduleBinding? = null
    private lateinit var adapter: ExamScheduleAdapter
    private var currentMonth: String = "11월" // 초기 월 설정

    private var scheduleService = RetrofitClient.retrofit.create(ApiService::class.java)
    private var certificationId: Int = -1
    private var scheduleId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExamScheduleBinding.inflate(inflater, container, false)
        certificationId = arguments?.getInt("CERTIFICATION_ID") ?: -1
        Log.d("ExamSchedule", "제대로 받아오고 있지? ${certificationId}")

        certificationName = arguments?.getString("CERTIFICATION_NAME") ?: ""

        setupRecyclerView()
        setupMonthNavigation(certificationId)
        setupRegisterButton()
        setupAddToScheduleButton()

        fetchExamSchedules(certificationId, currentMonth)

        return binding!!.root
    }

    private fun setupRecyclerView() {
        adapter = ExamScheduleAdapter(::onScheduleButtonSelectionChanged)
        binding?.scheduleRecyclerView?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.scheduleRecyclerView?.adapter = adapter
    }

    private fun setupMonthNavigation(certificationId: Int) {
        binding?.btnPreviousMonth?.setOnClickListener {
            getPreviousMonth(currentMonth)?.let { previousMonth ->
                fetchExamSchedules(certificationId, previousMonth)
            }
        }

        binding?.btnNextMonth?.setOnClickListener {
            getNextMonth(currentMonth)?.let { nextMonth ->
                fetchExamSchedules(certificationId, nextMonth)
            }
        }
    }

    private fun fetchExamSchedules(certificationId: Int, month: String) {
        currentMonth = month
        binding?.currentMonth?.text = month
        val monthItem: String
        if (currentMonth.length == 3) {
            monthItem = currentMonth.substring(0,2).trim()
        }
        else {
            monthItem = currentMonth.substring(0,1).trim()
        }

        val call = scheduleService.getCertificationIdMonth(
            authToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NDgsImVtYWlsIjoiZ2dzQGV4bWFwbGUuY29tIiwiaWF0IjoxNzQwMDYwMTA3LCJleHAiOjE3NDA2NjQ5MDd9.5yk8Bbe8oZLNir3epeMoF5F4FfIfD64iQhe9UGD57kI",
            provider = "",
            certificationId = certificationId.toString(),
            month = monthItem.toInt()
        )

        call.enqueue(object : Callback<UserResponseIdMonth> {
            override fun onResponse(
                call: Call<UserResponseIdMonth>, response: Response<UserResponseIdMonth>
            ) {
                Log.d("ExamSchedule", "조건문 앞까지 도착")
                if (response.isSuccessful && response.body() != null) {
                    val scheduleList = response.body()!!.dates
                    adapter.submitList(scheduleList)  // scheduleId 포함한 리스트 전달
                    binding.examName.text = certificationName
                    Log.d("ExamSchedule", "정상 작동 !")
                } else {
                    Log.e("ExamSchedule", "Response unsuccessful: ${response.code()}")
                    if (!response.isSuccessful) {
                        Log.e("ExamSchedule", "서버 연결 실패: ${response}")
                    }
                    adapter.submitList(emptyList())
                }
            }

            override fun onFailure(call: Call<UserResponseIdMonth>, t: Throwable) {
                Log.e("ExamSchedule", "Error: ${t.message}")
                adapter.submitList(emptyList())
            }
        })
    }

    private fun getPreviousMonth(current: String): String? {
        val months = listOf("1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월")
        val index = months.indexOf(current)
        return if (index != -1) months[(index - 1 + months.size) % months.size] else null
    }

    private fun getNextMonth(current: String): String? {
        val months = listOf("1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월")
        val index = months.indexOf(current)
        return if (index != -1) months[(index + 1) % months.size] else null
    }

    private fun setupRegisterButton() {
        binding.btnRegister.isEnabled = false

        binding.btnRegister.setOnClickListener {
            val selectedScheduleId = adapter.getSelectedScheduleId()

            if (selectedScheduleId != null) {
                Log.d("ExamSchedule", "선택된 scheduleId: $selectedScheduleId")
                applyForExam(certificationId.toString(), selectedScheduleId)
            } else {
                Toast.makeText(binding.root.context, "시험을 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun applyForExam(certificationId: String, scheduleId: Int) {
        val callApply = scheduleService.getCertificationSchedulesCheck(
            authToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NDgsImVtYWlsIjoiZ2dzQGV4bWFwbGUuY29tIiwiaWF0IjoxNzQwMDYwMTA3LCJleHAiOjE3NDA2NjQ5MDd9.5yk8Bbe8oZLNir3epeMoF5F4FfIfD64iQhe9UGD57kI",
            provider = "",
            scheduleId = scheduleId,
            certificationId = certificationId
        )

        callApply.enqueue(object : Callback<UserResponseScaduleCheck> {
            override fun onResponse(
                callApply: Call<UserResponseScaduleCheck>, response: Response<UserResponseScaduleCheck>
            ) {
                Log.e("ExamSchedule", "스케쥴 ${scheduleId}, 자격증 ${certificationId}")
                Log.e("ExamSchedule", "왜 안되는거지 ${response.isSuccessful}, $response")
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()?.message == "현재 접수기간입니다.") {
                        try {
//                            Toast.makeText(binding.root.context, "시험 접수 성공!", Toast.LENGTH_SHORT)
//                                .show()
                            val urlScheme = response.body()!!.examLink
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlScheme))
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(binding.root.context, "딥링크 호출이 불가능합니다.", Toast.LENGTH_SHORT).show()
                        }
                    } else if (response.body()?.message == "접수기간이 종료된 시험입니다. 다른 날짜의 시험을 선택하시기 바랍니다.") {
                        val dialog = ExamClosedPopUp(binding.root.context)
                        dialog.show()
                    } else {
                        val dialogMessage = response.body()!!.message
                        val dialog = ExamNotOpenPopUp(
                            binding.root.context,
                            dialogMessage,
                            certificationId,
                            userId = 2,
                            scheduleId = scheduleId
                        ) { isConfirmed ->
                            if (isConfirmed) { }
                        }
                        dialog.show()
                    }
                } else {
                    Toast.makeText(binding.root.context, "시험 접수 실패: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponseScaduleCheck>, t: Throwable) {
                Toast.makeText(binding.root.context, "네트워크 오류: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupAddToScheduleButton() {
        binding?.btnAddToSchedule?.visibility = View.GONE
        binding?.btnAddToSchedule?.setOnClickListener { showAddToSchedulePopup() }
    }

    private fun onScheduleButtonSelectionChanged(isAnyButtonSelected: Boolean) {
        binding?.btnRegister?.isEnabled = isAnyButtonSelected
        binding?.btnRegister?.backgroundTintList = requireContext().getColorStateList(
            if (isAnyButtonSelected) R.color.main_01 else R.color.grayscale_06
        )
        binding?.btnAddToSchedule?.visibility = if (isAnyButtonSelected) View.VISIBLE else View.GONE
    }

    private fun showConfirmationPopup(selectedText: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("선택 확인")
            .setMessage("$selectedText 일정이 선택되었습니다.")
            .setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun showAddToSchedulePopup() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_add_to_schedule_popup, null)
        val btnYes: Button = dialogView.findViewById(R.id.btn_yes)
        val btnNo: Button = dialogView.findViewById(R.id.btn_no)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()

        btnYes.setOnClickListener {
            Toast.makeText(requireContext(), "일정이 추가되었습니다.", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        btnNo.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

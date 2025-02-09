package com.example.gonggaksim_frontend

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gonggaksim_frontend.databinding.FragmentExamDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExamDetailFragment : Fragment() {

    private var _binding: FragmentExamDetailBinding? = null
    private val binding get() = _binding!!
    private val certiService = RetrofitClient.getRetrofit().create(certificateService::class.java)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExamDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        // "시험일정 추천받기" 버튼 클릭 이벤트 추가
        binding.btnExamSuggestion.setOnClickListener {
            openExamDivPointActivity()
        }

        // "시험일정 확인하기" 버튼 클릭 이벤트 추가
        binding.btnCheckSchedule.setOnClickListener {
            openExamScheduleFragment()
        }

        return view
    }

    private fun bindExamData(exam: ExamInfo) {
        binding.examCategory.text = exam.category
        binding.examName.text = exam.name
        binding.examQualification.text = exam.qualification
        binding.examQualificationDetail.text = exam.qualificationDetail
        binding.examSubjects.text = exam.subjects
        binding.examQuestionFormat.text = exam.questionFormat
        binding.examDuration.text = exam.duration
        binding.examPassingCriteria.text = exam.passingCriteria
        binding.examFee.text = exam.fee
        binding.examAnnouncement.text = exam.announcement
    }

    private fun openExamDivPointActivity() {
        val intent = Intent(requireContext(), ExamDivPointActivity::class.java)
        startActivity(intent)
    }

    private fun openExamScheduleFragment() {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, ExamScheduleFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun fetchCertificationDetails(certificationId: Int) {
        val call = certiService.getCertificationDetails(
            authToken = "Bearer YOUR_AUTH_TOKEN",
            provider = "providerName",
            category = certificationId.toString()
        )

        call.enqueue(object : Callback<UserResponseDetail> {
            override fun onResponse(call: Call<UserResponseDetail>, response: Response<UserResponseDetail>) {
                if (response.isSuccessful) {
                    val detailResponse = response.body()
                    if (detailResponse?.success == true) {
                        updateCertificationDetailUI(detailResponse.data)
                    } else {
                        Toast.makeText(context, detailResponse?.message ?: "상세 정보 조회 실패", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "응답 실패: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponseDetail>, t: Throwable) {
                Toast.makeText(context, "네트워크 오류 발생", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun updateCertificationDetailUI(detail: CertificationDetail?) {
        detail?.let {
            binding.examCategory.text = it.category
            binding.examName.text = it.name
            binding.examQualification.text = it.eligibility
            binding.examSubjects.text = it.subjects
            binding.examQuestionFormat.text = it.examFormat
            binding.examDuration.text = it.examDuration
            binding.examPassingCriteria.text = it.passingCriteria
            binding.examFee.text = it.fee
            binding.examAnnouncement.text = it.announcementSchedule
        }
    }
}
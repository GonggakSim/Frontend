package com.example.gonggaksim_frontend

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gonggaksim_frontend.api.ExamRecommendationRequest
import com.example.gonggaksim_frontend.api.ExamRecommendationResponse
import com.example.gonggaksim_frontend.databinding.FragmentExamDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExamDetailFragment : Fragment() {

    private lateinit var binding: FragmentExamDetailBinding
//    private val binding get() = _binding!!
    private val certiService = RetrofitClient.retrofit.create(ApiService::class.java)

    private lateinit var certificationName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExamDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        val certificationId = arguments?.getInt("CERTIFICATION_ID") ?: -1
        Log.d("SearchFragment", "제대로 수신 $certificationId")
        Log.d("ExamDetail", "제대로 수신 $certificationId")
        fetchCertificationDetails(certificationId)
        certificationName = ""

        // "시험일정 추천받기" 버튼 클릭 이벤트 추가
        binding.btnExamSuggestion.setOnClickListener {
            requestExamRecommendation()
            openExamDivPointActivity()
        }

        // "시험일정 확인하기" 버튼 클릭 이벤트 추가
        binding.btnCheckSchedule.setOnClickListener {
            openExamScheduleFragment(certificationId, certificationName)
        }

        return view
    }

    private fun requestExamRecommendation() {
        val request = ExamRecommendationRequest(
            userId = 1, // 실제 userId 가져오기
            name = "TOEIC",
            studyExperience = "초급",
            studyTimePerDay = "3~4시간",
            studyFrequency = "매일 조금씩",
            examGoal = "800"
        )

        RetrofitClient.scheduleApi.getExamRecommendation(provider = "", request).enqueue(object : Callback<ExamRecommendationResponse> {
            override fun onResponse(call: Call<ExamRecommendationResponse>, response: Response<ExamRecommendationResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data?.status == "success") {
                        Toast.makeText(context, "추천된 일정: ${data.data?.examDate}", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "추천 실패: ${data?.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "서버 응답 오류: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ExamRecommendationResponse>, t: Throwable) {
                Toast.makeText(context, "네트워크 오류 발생", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getToken(): String? {
        val sharedPreferences = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)
        return sharedPreferences.getString("accessToken", null)
    }

    private fun openExamDivPointActivity() {
        val intent = Intent(requireContext(), ExamDivPointActivity::class.java)
        startActivity(intent)
    }

    private fun openExamScheduleFragment(certificationId: Int, certificationName: String) {
        val bundle = Bundle()
        bundle.putInt("CERTIFICATION_ID", certificationId)


        val examScheduleFragment = ExamScheduleFragment().apply {
            arguments = Bundle().apply {
                putInt("CERTIFICATION_ID", certificationId)
                putString("CERTIFICATION_NAME", certificationName)
            }
        }
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, examScheduleFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
    private fun fetchCertificationDetails(certificationId: Int) {
        val token : String? = getToken()
        val call = certiService.getCertificationDetails(
            authToken = "Bearer ${token}",
            provider = "",
            certificationId = certificationId.toString()
        )

        call.enqueue(object : Callback<UserResponseDetail> {
            override fun onResponse(call: Call<UserResponseDetail>, response: Response<UserResponseDetail>) {
                if (response.isSuccessful) {
                    val detailResponse = response.body()
                    if (detailResponse?.success == true) {
                        updateCertificationDetailUI(detailResponse.data)
                        certificationName = detailResponse.data?.name ?: ""
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
            Log.d("ExamDetail", "${it.name}")
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
package com.example.gonggaksim_frontend

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.gonggaksim_frontend.databinding.FragmentMypageBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageFragment : Fragment() {
    val mypageService = RetrofitClient.getRetrofit().create(mypageService::class.java)
    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!  // 안전한 바인딩 참조

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)

        binding.tvLogoutBtn.setOnClickListener {
            showLogoutDialog()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.modifyBtn.setOnClickListener {
            val intent = Intent(requireContext(), ModifyInformationActivity::class.java)
            startActivity(intent)
        }

        binding.tvHelpBtn.setOnClickListener {
            val intent = Intent(requireContext(), HelpActivity::class.java)
            startActivity(intent)
        }

        binding.tvDeleteAccountBtn.setOnClickListener {
            val intent = Intent(requireContext(), MembershipWithdrawalActivity::class.java)
            startActivity(intent)
        }
        fetchUserData()
    }
    private fun fetchUserData() {
        val authToken = "Bearer ACCESS_TOKEN"
        val provider = "Google"

        mypageService.getUserMypage(authToken, provider).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { userResponse ->
                        userResponse.data?.let { userData ->
                            updateUI(userData)
                        } ?: run {
                            Log.e("MypageFragment", "데이터가 없음, 기본값 설정")
                            updateUI(getDefaultUserData())
                        }
                    }
                } else {
                    Log.e("MypageFragment", " API 응답 오류: ${response.code()} - ${response.message()}")
                    updateUI(getDefaultUserData())
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("MypageFragment", "API 호출 실패: ${t.message}")
                updateUI(getDefaultUserData())
            }
        })
    }

    private fun updateUI(userData: UserData) {
        binding.tvUserName.text = "${userData.name}님"
        binding.tvGreeting.text = "오늘도 화이팅하세요!"

        // 카테고리 목록을 문자열로 변환하여 표시
        binding.tvTags.text = userData.categories?.joinToString(", ") ?: "카테고리 없음"

        // 프로필 이미지 로드 (Glide 사용)
        Glide.with(this)
            .load(userData.profileImage)
            .into(binding.ivProfile)

        // 가장 가까운 시험 정보 업데이트
        userData.closestExams?.let { exams ->
            if (exams.isNotEmpty()) {
                val exam1 = exams[0]
                binding.tvExam1.text = exam1.name
                binding.tvExam1Dday.text = "D-${exam1.dDay}"
            } else {
                binding.tvExam1.text = "등록된 시험 없음"
                binding.tvExam1Dday.text = "D-?"
            }

            if (exams.size > 1) {
                val exam2 = exams[1]
                binding.tvExam2.text = exam2.name
                binding.tvExam2Dday.text = "D-${exam2.dDay}"
            } else {
                binding.tvExam2.text = "다음 시험 일정 없음"
                binding.tvExam2Dday.text = "D-?"
            }
        } ?: run {
            // 시험 정보가 null일 때 기본값 설정
            binding.tvExam1.text = "등록된 시험 없음"
            binding.tvExam1Dday.text = "D-?"

            binding.tvExam2.text = "다음 시험 일정 없음"
            binding.tvExam2Dday.text = "D-?"
        }
    }
    @SuppressLint("MissingInflatedId")
    private fun showLogoutDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val dialogBinding = com.example.gonggaksim_frontend.databinding.ActivityDialogLogoutBinding.inflate(layoutInflater)

        dialogBinding.btnConfirm.setOnClickListener {
            // 로그아웃 로직 실행 (예: SharedPreferences 삭제, 로그인 화면 이동)
            dialog.dismiss()
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss() // 팝업 닫기
        }

        dialog.setContentView(dialogBinding.root)
        dialog.show()
    }

    private fun getDefaultUserData(): UserData {
        return UserData(
            name = "게스트",
            profileImage = "https://example.com/default_profile.jpg", // 기본 프로필 이미지
            categories = listOf("기본 카테고리"),
            closestExams = listOf(
                Exam(name = "다음 시험 1", date = "2025-12-31", dDay = 13),
                Exam(name = "다음 시험 2", date = "2025-12-31", dDay = 27)
            )
        )
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // 메모리 누수 방지
    }
}
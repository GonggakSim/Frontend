package com.example.gonggaksim_frontend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.gonggaksim_frontend.databinding.FragmentMypageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageFragment : Fragment() {
    private lateinit var binding: FragmentMypageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding =FragmentMypageBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // API 요청 실행
        fetchMypageData()
    }

    private fun fetchMypageData() {
        val accessToken = "Bearer YOUR_ACCESS_TOKEN"  // OAuth2 토큰 (실제 토큰으로 변경 필요)
        val provider = "google"  // 또는 "kakao" 등

        RetrofitClient.instance.getUserMypage(accessToken, provider).enqueue(object :
            Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val userData = response.body()?.data
                    if (userData != null) {
                        updateUI(userData)
                    } else {
                        applyDefaultValues() // 데이터가 없을 경우 기본값 설정
                    }
                } else {
                    applyDefaultValues() // API 응답 실패 시 기본값 설정
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                applyDefaultValues() // 네트워크 오류 발생 시 기본값 설정
            }
        })
    }

    private fun updateUI(userData: UserData) {
        with(binding) {
            // 사용자 이름 설정
            tvUserName.text = "${userData.name}님"

            // 프로필 이미지 로드 (Glide 사용)
            Glide.with(this@MypageFragment)
                .load(userData.profileImage)
                .placeholder(R.drawable.app_icon) // 기본 이미지 (로딩 중)
                .error(R.drawable.app_icon) // 에러 발생 시 기본 이미지
                .into(ivProfile)

            // 관심 카테고리 표시
            tvTags.text = if (userData.categories.isNotEmpty()) {
                userData.categories.joinToString(" · ")
            } else {
                "관심 카테고리 없음"
            }

            // 다가오는 시험 정보
            val exams = userData.closestExams ?: DEFAULT_EXAMS
            if (exams.isNotEmpty()) {
                tvExam1.text = exams[0].name
                tvExam1Dday.text = "D-${exams[0].dDay}"
            } else {
                tvExam1.text = "등록된 시험 없음"
                tvExam1Dday.visibility = View.GONE
            }

            if (exams.size > 1) {
                tvExam2.text = exams[1].name
                tvExam2Dday.text = "D-${exams[1].dDay}"
            } else {
                tvExam2.visibility = View.GONE
                tvExam2Dday.visibility = View.GONE
            }
        }
    }

    private fun applyDefaultValues() {
        // 기본값으로 UI 업데이트
        val defaultData = UserData(
            name = DEFAULT_USER_NAME,
            profileImage = DEFAULT_PROFILE_IMAGE,
            categories = DEFAULT_CATEGORIES,
            closestExams = DEFAULT_EXAMS
        )
        updateUI(defaultData)
    }

    companion object {
        private const val DEFAULT_USER_NAME = "사용자"
        private const val DEFAULT_PROFILE_IMAGE = "https://example.com/default_profile.jpg" // 기본 프로필 이미지
        private val DEFAULT_CATEGORIES = listOf("전산","유통","물류","회계")
        private val DEFAULT_EXAMS = listOf(
            Exam("시험1", "2025-01-01T00:00:00.000Z", 10),
            Exam("시험2", "2025-01-01T00:00:00.000Z", 15)

        )
    }

}
package com.example.gonggaksim_frontend

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gonggaksim_frontend.databinding.FragmentTestBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestFragment : Fragment() {

    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!
    private val certiService = RetrofitClient.retrofit.create(ApiService::class.java)
    private val filteredData : MutableList<Certification> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        val view = binding.root

        setupRecyclerView()
        setupCategoryButtons()
        setInitialCategorySelection()
        setupSearchBarClickListener()
        setupInputExamButton()
        startScrollButtonAnimation()

        return view
    }

    private fun getToken(): String? {
        val sharedPreferences = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)
        return sharedPreferences.getString("accessToken", null)
    }

    private fun setupRecyclerView() {
        binding.examList.layoutManager = LinearLayoutManager(requireContext())

        // RecyclerView 어댑터 설정 및 아이템 클릭 이벤트 처리
        binding.examList.adapter = TestAdapter(requireContext(), filteredData) { certificationId ->
            navigateToExamDetailFragment(certificationId)  // certification_id 전달
        }

        binding.examList.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )
    }

    private fun startScrollButtonAnimation() {
        val blinkAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in_out)
        binding.scrollUpButton.startAnimation(blinkAnimation)
    }

    private fun setupSearchBarClickListener() {
        binding.searchBar.setOnClickListener {
            binding.searchBar.clearFocus()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, SearchFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun navigateToExamDetailFragment(certificationId: Int) {
        Log.d("certificationId", "Navigating with ID: $certificationId") // 디버깅 로그 추가
        val fragment = ExamDetailFragment().apply {
            arguments = Bundle().apply {
                putInt("CERTIFICATION_ID", certificationId)
                Log.d("certificationId", certificationId.toString()
                )// certification_id 전달
            }
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupInputExamButton() {
        binding.btnInputExam.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, ExamInputFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setupCategoryButtons() {
        val categories = mapOf(
            binding.categoryAll to "All",
            binding.categoryIt to "전산/IT",
            binding.categoryEnglish to "어학(한영일)",
            binding.categoryDesign to "환경/에너지",
            binding.categoryAccounting to "회계/재무",
            binding.categoryManagement to "경영/경제",
            binding.categoryLogistics to "물류/유통",
            binding.categorySafety to "안전/소방",
            binding.categoryEdu to "교육/상담",
            binding.categoryIndustry to "산업/기술",
            binding.categoryOthers to "기타"
        )

        categories.forEach { (button, categoryKey) ->
            button.setOnClickListener {
                categories.keys.forEach {
                    it.setBackgroundResource(R.drawable.category_default_background)
                    it.setTextColor(resources.getColor(R.color.grayscale_08, null))
                }

                button.setBackgroundResource(R.drawable.category_selected_background)
                button.setTextColor(resources.getColor(R.color.main_01, null))

                if (categoryKey == "All") {
                    fetchCertifications(null)
                } else {
                    fetchCertifications(categoryKey)
                }
            }
        }
    }

    private fun setInitialCategorySelection() {
        binding.categoryAll.setBackgroundResource(R.drawable.category_selected_background)
        binding.categoryAll.setTextColor(resources.getColor(R.color.main_01, null))
        fetchCertifications(null)  // 기본적으로 전체 목록을 가져옵니다.
    }

    private fun fetchCertifications(category: String?) {
        val token : String? = getToken()
        val call = if (category == null) {
            certiService.getAllCertifies("Bearer ${token}", "")
        } else {
            Log.e("fetch", "선택된 카테고리 : ${category}")
            certiService.getCategoryCertifies("Bearer ${token}", category,"")
        }

        call.enqueue(object : Callback<UserResponseCertification> { // ✅ 올바른 타입
            override fun onResponse(call: Call<UserResponseCertification>, response: Response<UserResponseCertification>) {
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    val certifications = userResponse?.data ?: emptyList() // ✅ data 필드에서 리스트 가져오기
                    Log.d("API Response", "받은 자격증 데이터 개수: ${certifications.size}")
                    Log.d("API Response", "받은 자격증 데이터 : ${certifications}")
                    updateMainRecyclerView(certifications)
                } else {
                    Log.e("API Response", "HTTP 응답 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserResponseCertification>, t: Throwable) {
                Log.e("NetworkError", "네트워크 요청 실패: ${t.message}")
            }
        })
    }

    private fun updateMainRecyclerView(data: List<Certification>) {
        filteredData.clear()
        filteredData.addAll(data)
        binding.examList.adapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
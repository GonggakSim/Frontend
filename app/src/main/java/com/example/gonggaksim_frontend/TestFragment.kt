package com.example.gonggaksim_frontend

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
    private val certiService = RetrofitClient.getRetrofit().create(certificateService::class.java)
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

    private fun setupRecyclerView() {
        binding.examList.layoutManager = LinearLayoutManager(requireContext())

        // RecyclerView 어댑터 설정 및 아이템 클릭 이벤트 처리
        binding.examList.adapter = TestAdapter(requireContext(), filteredData) { certification ->
            navigateToExamDetailFragment(certification.certification_id)  // certification_id 전달
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
        val fragment = ExamDetailFragment().apply {
            arguments = Bundle().apply {
                putInt("CERTIFICATION_ID", certificationId)  // certification_id 전달
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
            binding.categoryIt to "전산",
            binding.categoryEnglish to "어학(영)",
            binding.categoryJapanese to "어학(중/일)",
            binding.categoryDesign to "디자인",
            binding.categoryAccounting to "회계/재무",
            binding.categoryManagement to "경영/경제",
            binding.categoryItAdvanced to "전산/IT",
            binding.categoryLogistics to "물류/유통",
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
        val call = if (category == null) {
            certiService.getAllCertifies("Bearer AUTH_Token", "kakao")
        } else {
            Log.e("fetch", "선택된 카테고리 : ${category}")
            certiService.getCategoryCertifies("Bearer AUTH_Token", category, "kakao")

        }

        call.enqueue(object : Callback<UserResponseCertification> {
            override fun onResponse(call: Call<UserResponseCertification>, response: Response<UserResponseCertification>) {
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    if (userResponse?.success == true) {
                        val certifications = listOfNotNull(userResponse.data)  // Certification 객체 리스트 생성
                        updateMainRecyclerView(certifications)  // List<Certification> 전달
                    } else {
                        Toast.makeText(context, userResponse?.message ?: "조회 실패", Toast.LENGTH_SHORT).show()
                        showTestData()
                    }
                } else {
                    Toast.makeText(requireContext(), "응답 실패: ${response.code()}", Toast.LENGTH_SHORT).show()
                    showTestData()
                }
            }

            override fun onFailure(call: Call<UserResponseCertification>, t: Throwable) {
                Toast.makeText(requireContext(), "네트워크 오류 발생", Toast.LENGTH_SHORT).show()
                showTestData()
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
    private fun showTestData() {
        val testData = listOf(
            "컴퓨터활용능력 1급 필기",
            "MOS Master",
            "ITQ 한글",
            "정보처리기사",
            "네트워크 관리사 2급"
        )
        val certificationList = testData.map { Certification(0, it, "기본 카테고리") }
        updateMainRecyclerView(certificationList)  // 변환된 List<Certification> 전달
        Toast.makeText(requireContext(), "테스트 데이터를 표시합니다.", Toast.LENGTH_SHORT).show()
    }

}
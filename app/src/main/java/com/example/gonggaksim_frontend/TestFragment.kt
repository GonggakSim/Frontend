package com.example.gonggaksim_frontend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TestFragment : Fragment() {

    private lateinit var searchBar: EditText
    private lateinit var backButton: ImageView
    private lateinit var mainRecyclerView: RecyclerView
    private lateinit var searchIcon: ImageView
    private lateinit var btnInputExam: Button
    private lateinit var scrollUpButton: ImageButton

    private lateinit var categoryAll: TextView
    private lateinit var categoryIT: TextView
    private lateinit var categoryEnglish: TextView
    private lateinit var categoryJapanese: TextView
    private lateinit var categoryDesign: TextView
    private lateinit var categoryAccounting: TextView
    private lateinit var categoryManagement: TextView
    private lateinit var categoryITAdvanced: TextView
    private lateinit var categoryLogistics: TextView
    private lateinit var categoryOthers: TextView

    private val filteredData = mutableListOf<String>()
    private val totalData = DataProvider.allData // 전체 데이터를 가져옴

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_test, container, false)

        // UI 초기화
        searchBar = view.findViewById(R.id.search_bar)
        backButton = view.findViewById(R.id.back_button)
        mainRecyclerView = view.findViewById(R.id.exam_list)
        searchIcon = view.findViewById(R.id.search_icon)
        btnInputExam = view.findViewById(R.id.btn_input_exam)
        scrollUpButton = view.findViewById(R.id.scroll_up_button)

        // 카테고리 버튼 초기화
        categoryAll = view.findViewById(R.id.category_all)
        categoryIT = view.findViewById(R.id.category_it)
        categoryEnglish = view.findViewById(R.id.category_english)
        categoryJapanese = view.findViewById(R.id.category_japanese)
        categoryDesign = view.findViewById(R.id.category_design)
        categoryAccounting = view.findViewById(R.id.category_accounting)
        categoryManagement = view.findViewById(R.id.category_management)
        categoryITAdvanced = view.findViewById(R.id.category_it_advanced)
        categoryLogistics = view.findViewById(R.id.category_logistics)
        categoryOthers = view.findViewById(R.id.category_others)

        // RecyclerView 설정
        mainRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        mainRecyclerView.adapter = TestAdapter(requireContext(), filteredData) { examName ->
            navigateToExamDetailFragment(examName)
        }

        // 초기 데이터 설정
        updateMainRecyclerView(DataProvider.allData)

        // 카테고리 버튼 클릭 이벤트 설정
        setupCategoryButtons()

        // **초기 선택: "전체" 카테고리**
        setInitialCategorySelection()

        // 검색바 클릭 이벤트 설정
        setupSearchBarClickListener()

        // btn_input_exam 클릭 이벤트 설정
        btnInputExam.setOnClickListener {
            navigateToExamInputFragment()
        }
        // Divider 추가
        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        mainRecyclerView.addItemDecoration(itemDecoration)

        startScrollButtonAnimation()

        return view
    }

    private fun startScrollButtonAnimation() {
        val blinkAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in_out)
        scrollUpButton.startAnimation(blinkAnimation)
    }

    // 검색바 클릭 시 SearchFragment로 이동
    private fun setupSearchBarClickListener() {
        searchBar.setOnClickListener {
            // 키보드 동작 방지
            searchBar.clearFocus()

            // SearchFragment로 이동
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, SearchFragment()) // SearchFragment로 전환
                .addToBackStack(null) // 뒤로 가기 버튼으로 돌아갈 수 있도록 설정
                .commit()
        }
    }

    // ExamDetailFragment로 이동하는 함수
    private fun navigateToExamDetailFragment(examName: String) {
        val fragment = ExamDetailFragment().apply {
            arguments = Bundle().apply {
                putString("EXAM_NAME", examName)
            }
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment) // fragment_container를 ExamDetailFragment로 대체
            .addToBackStack(null) // 뒤로 가기 버튼을 통해 이전 화면으로 돌아갈 수 있도록 설정
            .commit()
    }

    // ExamInputFragment로 이동하는 함수
    private fun navigateToExamInputFragment() {
        val fragment = ExamInputFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupCategoryButtons() {
        val categories = mapOf(
            categoryAll to "All",
            categoryIT to "IT",
            categoryEnglish to "English",
            categoryJapanese to "Japanese",
            categoryDesign to "Design",
            categoryAccounting to "Accounting",
            categoryManagement to "Management",
            categoryITAdvanced to "ITAdvanced",
            categoryLogistics to "Logistics",
            categoryOthers to "Others"
        )

        categories.forEach { (button, categoryKey) ->
            button.setOnClickListener {
                // 1. 모든 버튼의 배경을 기본 배경으로 초기화
                categories.keys.forEach {
                    it.background = resources.getDrawable(R.drawable.category_default_background, null)
                    it.setTextColor(resources.getColor(R.color.grayscale_08, null)) // 텍스트 색상 초기화
                }

                // 2. 클릭된 버튼에 선택된 배경과 텍스트 색상 설정
                button.background = resources.getDrawable(R.drawable.category_selected_background, null)
                button.setTextColor(resources.getColor(R.color.main_01, null))

                // 3. RecyclerView 데이터 업데이트
                val filteredDataList = DataProvider.categoryDataMap[categoryKey] ?: DataProvider.allData
                updateMainRecyclerView(filteredDataList)
            }
        }
    }

    private fun setInitialCategorySelection() {
        // 초기 상태: "전체" 카테고리 선택
        categoryAll.background = resources.getDrawable(R.drawable.category_selected_background, null)
        categoryAll.setTextColor(resources.getColor(R.color.main_01, null))
    }

    private fun updateMainRecyclerView(data: List<String>) {
        filteredData.clear()
        filteredData.addAll(data)
        mainRecyclerView.adapter?.notifyDataSetChanged()
    }
}

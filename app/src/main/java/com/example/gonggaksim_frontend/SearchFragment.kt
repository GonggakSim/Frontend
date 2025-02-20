package com.example.gonggaksim_frontend

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private lateinit var searchBar: EditText
    private lateinit var searchIcon: ImageView
    private lateinit var backButton: ImageView
    private lateinit var recentSearchChipGroup: ChipGroup
    private lateinit var autoCompleteRecyclerView: RecyclerView
    private lateinit var clearAllButton: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dataId: String

    private val recentSearches = mutableListOf<String>()
    private val searchSuggestions = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        dataId = "-1"

        // View 초기화
        searchBar = view.findViewById(R.id.search_bar)
        searchIcon = view.findViewById(R.id.search_icon)
        backButton = view.findViewById(R.id.back_button)
        recentSearchChipGroup = view.findViewById(R.id.chipGroupRecentSearches)
        autoCompleteRecyclerView = view.findViewById(R.id.autoCompleteRecyclerView)
        clearAllButton = view.findViewById(R.id.clear_all_button)

        // SharedPreferences 초기화
        sharedPreferences = requireContext().getSharedPreferences("recent_searches", AppCompatActivity.MODE_PRIVATE)
        loadRecentSearches()

        // RecyclerView 초기화
        autoCompleteRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        autoCompleteRecyclerView.adapter = AutoCompleteAdapter(searchSuggestions) { query ->
            searchBar.setText(query)
            saveRecentSearch(query)
            showToast("검색 결과: $query")
            navigateToExamDetailFragment(dataId)
        }

        // 검색창 이벤트 설정
        setupSearchBar()

        // 검색 아이콘 클릭 이벤트
        setupSearchIconClickListener()

        // 뒤로가기 버튼 클릭 이벤트
        setupBackButtonClickListener()

        // 전체 삭제 버튼 클릭 이벤트
        setupClearAllButton()

        return view
    }

    private fun setupSearchBar() {
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (query.isBlank()) {
                    backButton.visibility = View.GONE
                    recentSearchChipGroup.visibility = View.VISIBLE
                    autoCompleteRecyclerView.visibility = View.GONE
                } else {
                    backButton.visibility = View.VISIBLE
                    recentSearchChipGroup.visibility = View.GONE
                    autoCompleteRecyclerView.visibility = View.VISIBLE
/*                    searchSuggestions.clear()
                    searchSuggestions.addAll(
                        DataProvider.allData.filter { it.contains(query, ignoreCase = true) }
                    )
                    autoCompleteRecyclerView.adapter?.notifyDataSetChanged()*/
                    // 🔹 API 호출하여 검색어 자동완성 리스트 가져오기
                    fetchSearchResults(query)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // 🔹 API 호출 함수
    private fun fetchSearchResults(query: String) {
        Log.d("SearchFragment", "fetchSearchResults 도착 ${query}")
        RetrofitClient.apiService.searchCertificates(
            authToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTYsImVtYWlsIjoiZGxhdG5xbHM5MjFAZGF1bS5uZXQiLCJpYXQiOjE3Mzk5NzA5MTgsImV4cCI6MTc0MDU3NTcxOH0.4g_UwVYhT4Uj-tfmnVnEu8fVLS7mzf5cHtlyHnUXU2I",
            provider = "",
            query = query,
            category = "",
        ).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                Log.d("SearchFragment", "fetchSearch 함수는 작동함")
                if (response.isSuccessful) {
                    Log.d("SearchFragment", "서버 통신 성공")
                    val body = response.body()
                    if (body != null && body.success) {
                        Log.d("SearchFragment", "응답 바디 있음 ${body.data}")
                        val results = body.data
                        for(i in results) {
                            Log.d("SearchFragment", "id 제대로 저장 ${i.name} ${query}")
                            if(i.name.toString() == query.toString()) {
                                dataId = i.id.toString()
                                Log.d("SearchFragment", "dataId 제대로 저장 $dataId")
                                navigateToExamDetailFragment(dataId)
                            }
                        }

                        // RecyclerView 업데이트
                        searchSuggestions.clear()
                        searchSuggestions.addAll(results.map { it.name })
                        autoCompleteRecyclerView.adapter?.notifyDataSetChanged()
                    } else {
                        // success가 false일 경우 메시지 출력
                        showToast("검색 실패: ${body?.message}")
                    }
                } else {
                    Log.e("API Error", "서버 오류 발생: ${response.code()} - ${response.message()}")
                    showToast("서버 응답 오류: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                showToast("네트워크 오류 발생: ${t.message}")
            }
        })
    }



    private fun setupSearchIconClickListener() {
        searchIcon.setOnClickListener {
            val query = searchBar.text.toString()
            Log.d("SearchFragment", "setupSearchIconClickListener에서 쿼리 정상적으로 초기화함")
            if (query.isNotBlank()) {
                showToast("검색: $query")
                saveRecentSearch(query)
            } else {
                showToast("검색어를 입력해주세요.")
            }
        }
    }

    private fun navigateToExamDetailFragment(dataId: String) {
        val fragment = ExamDetailFragment().apply {
            arguments = Bundle().apply {
                putInt("CERTIFICATION_ID", dataId.toInt())
                Log.d("SearchFragment", "다음 화면으로 넘어가기 직전 $dataId")
            }
        }
        requireActivity().runOnUiThread {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }

    private fun setupBackButtonClickListener() {
        backButton.setOnClickListener {
            requireActivity().runOnUiThread {
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun setupClearAllButton() {
        clearAllButton.setOnClickListener {
            clearAllChips()
        }
    }

    private fun loadRecentSearches() {
        recentSearches.clear()
        recentSearches.addAll(sharedPreferences.getStringSet("recent_searches", emptySet()) ?: emptySet())
        Log.d("SearchFragment", "1. 쿼리 변경 함수")
        updateRecentSearchChips()
    }

    private fun saveRecentSearch(query: String) {
        if (!recentSearches.contains(query)) {
            recentSearches.add(0, query)
            if (recentSearches.size > 10) recentSearches.removeAt(10)
            saveRecentSearches()
            Log.d("SearchFragment", "2. 쿼리 변경 함수")
            updateRecentSearchChips()
        }
    }

    private fun saveRecentSearches() {
        sharedPreferences.edit().putStringSet("recent_searches", recentSearches.toSet()).apply()
    }

    private fun updateRecentSearchChips() {
        Log.d("SearchFragment", "3. 쿼리 변경 함수")
        // ChipGroup의 모든 뷰를 제거합니다.
        recentSearchChipGroup.removeAllViews()

        // 최대 10개의 칩만 표시하도록 합니다.
        val maxChips = 10
        val displaySearches = recentSearches.take(maxChips)


        // 각 검색어에 대해 칩을 생성하고 ChipGroup에 추가합니다.
        displaySearches.forEach { search ->
            val chip = Chip(requireContext()).apply {
                Log.d("SearchFragment", "1) 쿼리 변경 ${search}")
                text = search
                setOnClickListener {
                    Log.d("SearchFragment", "2-1) 쿼리 변경 ${search}")
                    searchBar.setText(search)
                    Log.d("SearchFragment", "2) 쿼리 변경 ${search}")
                    fetchSearchResults(query = search) // query 값을 업데이트
                }

                setOnCloseIconClickListener { removeChip(search) }
                isCloseIconVisible = true

                // 스타일 속성 직접 설정
                chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.grayscale_01)
                chipStrokeColor = ContextCompat.getColorStateList(context, R.color.chip_stroke)
                chipStrokeWidth = 1.5f
                setTextColor(ContextCompat.getColor(context, R.color.chip_text))
                setTextAppearance(R.style.CustomChipTextAppearance)
                // 스타일 적용
                setChipDrawable(
                    ChipDrawable.createFromAttributes(
                        requireContext(),
                        null,
                        0,
                        R.style.CustomChipStyle
                    )
                )
                // 닫기 아이콘 다시 설정
                isCloseIconVisible = true
                setOnCloseIconClickListener { removeChip(search) }
            }
            // ChipGroup에 칩을 추가합니다.
            recentSearchChipGroup.addView(chip)
        }
    }

    private fun clearAllChips() {
        recentSearches.clear()
        saveRecentSearches()
        Log.d("SearchFragment", "4. 쿼리 변경 함수")
        updateRecentSearchChips()
    }

    private fun removeChip(search: String) {
        if (recentSearches.contains(search)) {
            recentSearches.remove(search)
            saveRecentSearches()
            Log.d("SearchFragment", "5. 쿼리 변경 함수")
            updateRecentSearchChips()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}
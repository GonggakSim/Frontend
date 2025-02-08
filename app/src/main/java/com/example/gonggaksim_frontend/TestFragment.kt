package com.example.gonggaksim_frontend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gonggaksim_frontend.databinding.FragmentTestBinding

class TestFragment : Fragment() {

    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!

    private val filteredData = mutableListOf<String>()
    private val totalData = DataProvider.allData

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
        binding.examList.adapter = TestAdapter(requireContext(), filteredData) { examName ->
            navigateToExamDetailFragment(examName)
        }
        binding.examList.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )
        updateMainRecyclerView(DataProvider.allData)
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

    private fun navigateToExamDetailFragment(examName: String) {
        val fragment = ExamDetailFragment().apply {
            arguments = Bundle().apply { putString("EXAM_NAME", examName) }
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
            binding.categoryIt to "IT",
            binding.categoryEnglish to "English",
            binding.categoryJapanese to "Japanese",
            binding.categoryDesign to "Design",
            binding.categoryAccounting to "Accounting",
            binding.categoryManagement to "Management",
            binding.categoryItAdvanced to "ITAdvanced",
            binding.categoryLogistics to "Logistics",
            binding.categoryOthers to "Others"
        )

        categories.forEach { (button, categoryKey) ->
            button.setOnClickListener {
                categories.keys.forEach {
                    it.setBackgroundResource(R.drawable.category_default_background)
                    it.setTextColor(resources.getColor(R.color.grayscale_08, null))
                }

                button.setBackgroundResource(R.drawable.category_selected_background)
                button.setTextColor(resources.getColor(R.color.main_01, null))

                val filteredDataList = DataProvider.categoryDataMap[categoryKey] ?: DataProvider.allData
                updateMainRecyclerView(filteredDataList)
            }
        }
    }

    private fun setInitialCategorySelection() {
        binding.categoryAll.setBackgroundResource(R.drawable.category_selected_background)
        binding.categoryAll.setTextColor(resources.getColor(R.color.main_01, null))
    }

    private fun updateMainRecyclerView(data: List<String>) {
        filteredData.clear()
        filteredData.addAll(data)
        binding.examList.adapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
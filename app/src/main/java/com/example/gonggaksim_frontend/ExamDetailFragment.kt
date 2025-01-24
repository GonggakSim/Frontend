package com.example.gonggaksim_frontend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ExamDetailFragment : Fragment() {

    private lateinit var examNameTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exam_detail, container, false)

        // View 초기화
        examNameTextView = view.findViewById(R.id.exam_name)

        // 번들에서 데이터 가져오기
        val examName = arguments?.getString("EXAM_NAME") ?: "시험 이름 없음"
        examNameTextView.text = examName

        return view
    }
}

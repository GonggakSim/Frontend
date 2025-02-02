package com.example.gonggaksim_frontend

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class ExamDetailFragment : Fragment() {

    private lateinit var examCategory: TextView
    private lateinit var examName: TextView
    private lateinit var examQualification: TextView
    private lateinit var examQualificationDetail: TextView
    private lateinit var examSubjects: TextView
    private lateinit var examQuestionFormat: TextView
    private lateinit var examDuration: TextView
    private lateinit var examPassingCriteria: TextView
    private lateinit var examFee: TextView
    private lateinit var examAnnouncement: TextView
    private lateinit var btnExamSuggestion: Button
    private lateinit var btnCheckSchedule: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exam_detail, container, false)

        // View 초기화
        examCategory = view.findViewById(R.id.exam_category)
        examName = view.findViewById(R.id.exam_name)
        examQualification = view.findViewById(R.id.exam_qualification)
        examQualificationDetail = view.findViewById(R.id.exam_qualification_detail)
        examSubjects = view.findViewById(R.id.exam_subjects)
        examQuestionFormat = view.findViewById(R.id.exam_question_format)
        examDuration = view.findViewById(R.id.exam_duration)
        examPassingCriteria = view.findViewById(R.id.exam_passing_criteria)
        examFee = view.findViewById(R.id.exam_fee)
        examAnnouncement = view.findViewById(R.id.exam_announcement)
        btnExamSuggestion = view.findViewById(R.id.btn_exam_suggestion)
        btnCheckSchedule = view.findViewById(R.id.btn_check_schedule)

        // 번들에서 데이터 가져오기
        val examNameArg = arguments?.getString("EXAM_NAME") ?: return view
        val exam = ExamDataProvider.exams[examNameArg] ?: return view

        // 데이터를 바인딩
        bindExamData(exam)

        // "시험일정 추천받기" 버튼 클릭 이벤트 추가
        btnExamSuggestion.setOnClickListener {
            openExamDivPointActivity()
        }

        // "시험일정 확인하기" 버튼 클릭 이벤트 추가
        btnCheckSchedule.setOnClickListener {
            openExamScheduleFragment()
        }

        return view
    }

    private fun bindExamData(exam: Exam) {
        examCategory.text = exam.category
        examName.text = exam.name
        examQualification.text = exam.qualification
        examQualificationDetail.text = exam.qualificationDetail
        examSubjects.text = exam.subjects
        examQuestionFormat.text = exam.questionFormat
        examDuration.text = exam.duration
        examPassingCriteria.text = exam.passingCriteria
        examFee.text = exam.fee
        examAnnouncement.text = exam.announcement
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
}

package com.example.gonggaksim_frontend

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.gonggaksim_frontend.databinding.FragmentExamInputBinding
import org.w3c.dom.Text

class ExamInputFragment : Fragment() {
    private lateinit var binding: FragmentExamInputBinding

    private lateinit var startDateYear: String
    private lateinit var startDateMonth: String
    private lateinit var startDateDay: String
    private lateinit var endDateYear: String
    private lateinit var endDateMonth: String
    private lateinit var endDateDay: String
    private lateinit var nameData: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExamInputBinding.inflate(inflater, container, false)

        clickButtonEvent()
        editTextEvent()

        return binding.root
    }

    private fun clickButtonEvent() {
        binding.examDateEt.setOnClickListener {
            val modalBottomSheet = ModalBottomSheet.newInstance()

            modalBottomSheet.show(parentFragmentManager, "ModalBottomSheet")

            // ModalBottomSheet에서 날짜 범위 선택 후 콜백 처리
            modalBottomSheet.onDateRangeSelected = { dateRange ->
                // 선택된 날짜 범위가 examDateEt에 표시되도록 설정
                binding.examDateEt.setText(dateRange)
            }
        }

        binding.examNameBtn.setOnClickListener {
            binding.examNameEt.getText().clear()
        }
        binding.examDateBtn.setOnClickListener {
            binding.examDateEt.getText().clear()
        }

        binding.esfBtn.setOnClickListener {
            Log.d("ModalBottomSheet", "여기까진 멀쩡하게 들어옴")
            if (!binding.examNameEt.text.toString().trim().isEmpty() && !binding.examDateEt.text.toString().trim().isEmpty()) {
                Log.d("ModalBottomSheet", "둘다 작성된 조건문")

                val examDateText = binding.examDateEt.text.toString()
                Log.d("ModalBottomSheet", "받은 일정의 날짜: $examDateText")

                // 날짜 범위 인덱스를 안전하게 처리 (예시: 날짜 형식이 고정적이라고 가정)
                if (examDateText.length > 15) {
                    startDateYear = examDateText.substring(0, 4).trim()
                    startDateMonth = examDateText.substring(5, 7).trim()
                    startDateDay = examDateText.substring(8, 10).trim()
                    endDateYear = examDateText.substring(11, 15).trim()
                    endDateMonth = examDateText.substring(16, 18).trim()
                    endDateDay = examDateText.substring(19, examDateText.length).trim()
                    Log.d("ModalBottomSheet", "받은 일정의 날짜: $startDateYear 년 $startDateMonth 월 $startDateDay 일")
                    Log.d("ModalBottomSheet", "받은 일정의 날짜: $endDateYear 년 $endDateMonth 월 $endDateDay 일")
                } else {
                    // 날짜 형식이 맞지 않으면 예외 처리를 할 수 있습니다
                    Log.e("ModalBottomSheet", "날짜 형식이 올바르지 않습니다.")
                }

                nameData = binding.examNameEt.text.toString()

                val newExamEventDate = "${startDateYear}년 ${startDateMonth}월 ${startDateDay}일 ${endDateYear}년 ${endDateMonth}월 ${endDateDay}일 $nameData"
                Log.d("ModalBottomSheet", "새로운 일정: $newExamEventDate")

                DateEventSingleton.dateEvent.addEvent(newExamEventDate)

                val fragmentCalendar = CalenderFragment()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_container, fragmentCalendar)
                    .commit()
            }

            else if(binding.examNameEt.text.toString().trim().isEmpty() && !binding.examDateEt.text.toString().trim().isEmpty()) {
                Log.d("ModalBottomSheet", "시험명 좀 적어줘요")
                binding.examNameEt.setHint("시험명을 작성해주세요!")
                binding.examNameEt.setHintTextColor(Color.parseColor("#EB4335"))
            }
            else if(!binding.examNameEt.text.toString().trim().isEmpty() && binding.examDateEt.text.toString().trim().isEmpty()) {
                Log.d("ModalBottomSheet", "시험날짜 좀 골라줘요")
                binding.examDateEt.setHint("시험날짜를 선택해주세요")
                binding.examDateEt.setHintTextColor(Color.parseColor("#EB4335"))
            }
            else {
                Log.d("ModalBottomSheet", "아무것도 안썼네요")
                binding.examNameEt.setHint("시험명을 작성해주세요!")
                binding.examNameEt.setHintTextColor(Color.parseColor("#EB4335"))
                binding.examDateEt.setHint("시험날짜를 선택해주세요")
                binding.examDateEt.setHintTextColor(Color.parseColor("#EB4335"))
            }
        }
    }
    private fun editTextEvent() {
        binding.examNameEt.addTextChangedListener {
            val text = binding.examNameEt.text.toString()
            if(text.isEmpty()) {
                binding.examNameBtn.visibility = View.GONE
            } else {
                binding.examNameBtn.visibility = View.VISIBLE
            }
        }

        binding.examDateEt.addTextChangedListener {
            val text = binding.examDateEt.text.toString()
            if(text.isEmpty()) {
                binding.examDateBtn.visibility = View.GONE
            } else {
                binding.examDateBtn.visibility = View.VISIBLE
            }
        }
    }
}
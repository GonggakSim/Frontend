package com.example.gonggaksim_frontend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.gonggaksim_frontend.databinding.FragmentCalenderBinding

class CalenderFragment : Fragment() {

    // 뷰 바인딩 - null이 아닐 때만 접근하도록 _binding 사용
    private var _binding: FragmentCalenderBinding? = null
    private val binding get() = _binding!!

    // 이후에 MonthAdapter와 연결하기 위한 변수
    private lateinit var monthAdapter: MonthAdapter

    // Fragment View 생성
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // 뷰 바인딩 진행
        _binding = FragmentCalenderBinding.inflate(inflater, container, false)
        val view = inflater.inflate(R.layout.fragment_calender, container, false)

        // 일정 직접 추가 버튼 클릭 이벤트
        binding.plsBtn.setOnClickListener {
            val fragmentExamInput = ExamInputFragment()
            // 현재 프레그먼트를 교체하는 방식
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragmentExamInput)
                .commit()
        }

        setListener()

        return binding.root
    }

    fun setListener() {
        initCalendar()
    }

    // 캘린더 초기화
    fun initCalendar(){

        // date 값 주고 MonthAdpater 생성
        monthAdapter = MonthAdapter(this, DateEvent().date)
        // RecyclerView로 캘린더 사용, 수평 스크롤 되는 레이아웃 메니저 적용
        // .apply를 사용하여 여러 설정을 체이닝 가능
        binding.customCalendar.apply {
            // false는 역방향 스크롤 막기용
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            // 어뎁터 연결
            adapter = monthAdapter
            // 초기 스크롤을 달력 중앙 위치로 설정
            scrollToPosition(Int.MAX_VALUE/2)
        }
        // 스크롤 기능 추가
        val snap = PagerSnapHelper()
        // 플링 리스너 체크
        if(binding.customCalendar.onFlingListener == null){
            // 스와이프 할 때 캘린더 한페이지 넘어감
            snap.attachToRecyclerView(binding.customCalendar)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
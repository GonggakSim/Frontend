package com.example.gonggaksim_frontend

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Date

// RecyclerView.Adapter 상속, 날짜 리스트 매개변수로 받음
class MonthAdapter(var context: CalenderFragment, val date:ArrayList<String>):
    RecyclerView.Adapter<MonthAdapter.MonthViewHolder>() {
        // RecyclerView의 중앙 위치 기준점
        val center = Int.MAX_VALUE/2
    // 현재 날짜와 시간을 관리하는 객체
    private var calendar = Calendar.getInstance()

    // RecyclerView의 각 아이템 뷰를 관리하며, layout 뷰를 받아서 뷰홀더 초기화
    inner class MonthViewHolder(val layout: View): RecyclerView.ViewHolder(layout)

    // RecyclerView의 각 아이템 뷰 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_month, parent, false)

        // 뷰를 생성하여 MonthViewHolder에 전달
        return MonthViewHolder(view)
    }

    // 각 아이템 뷰에 데이터 바인딩. position은 현재 아이템의 위치
    @SuppressLint("SetText| 18n")
    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        // 현재 날짜를 가져옴
        calendar.time = Date()
        // 날짜를 1일로 초기화, 해당 월의 첫 번째 날로 설정
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        // position에 따라 해당 월로 이동하며, 중앙 위치를 기준으로 스크롤
        calendar.add(Calendar.MONTH, position-center)

        // 현재 설정된 월을 converMonth() 메서드를 따라 텍스트 뷰 설정
        holder.layout.findViewById<TextView>(R.id.fragment_calender_dateMonth).text =
            "${converMonth(calendar.get(Calendar.MONTH) + 1)}"

        // 현재 설정된 월 저장
        val tmpMonth = calendar.get(Calendar.MONTH)
        // 캘린더에 보여줄 날짜 저장 - 각 주는 7일이며 주는 6주로 설정
        var dayList: MutableList<Date> = MutableList(6*7){Date()}

        // 날짜 리스트 채우기 - 첫 반복문: 주, 두 번째 반복문: 요일
        for(i in 0..5) {
            for(j in 0..6) {
                // 캘린더 변수를 조정해서 각 주의 날짜를 리스트에 저장
                calendar.add(Calendar.DAY_OF_MONTH, (1-calendar.get(Calendar.DAY_OF_WEEK)) + j)
                dayList[i*7+j] = calendar.time
            }
            calendar.add(Calendar.WEEK_OF_MONTH, 1)
        }

        // 일 어댑터 생성 - 현재 월, 날짜 리스트, 일정 있는 날짜 리스트 전송
        var dayAdapter = DayAdapter(tmpMonth, dayList, date)
        // RecyclerView와 연결
        holder.layout.findViewById<RecyclerView>(R.id.fragment_calender_dayRv).apply {
            // GridLayoutManager를 사용하여 7개의 열로 된 그리드 레이아웃 설정 후 연결
            layoutManager = GridLayoutManager(holder.layout.context, 7)
            adapter = dayAdapter
        }
    }

    // RecyclerView에 표시할 아이템 수 반환 - MAX_VALUE를 사용하여 무한 스크롤 가능(스와이프로 월 무한하게 전환 가능)
    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    // 각 월을 문자열로 반환하는 메서드
    fun converMonth(month: Int): String {
        if(month == 1) {
            return "1월"
        }
        if(month == 2) {
            return "2월"
        }
        if(month == 3) {
            return "3월"
        }
        if(month == 4) {
            return "4월"
        }
        if(month == 5) {
            return "5월"
        }
        if(month == 6) {
            return "6월"
        }
        if(month == 7) {
            return "7월"
        }
        if(month == 8) {
            return "8월"
        }
        if(month == 9) {
            return "9월"
        }
        if(month == 10) {
            return "10월"
        }
        if(month == 11) {
            return "11월"
        }
        if(month == 12) {
            return "12월"
        }
        return ""
    }

}
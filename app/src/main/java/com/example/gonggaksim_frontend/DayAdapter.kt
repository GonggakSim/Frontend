package com.example.gonggaksim_frontend

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gonggaksim_frontend.databinding.ItemCalendarDayBinding
import java.util.*

class DayAdapter(val tmpMonth: Int, val dayList: MutableList<Date>, val date: ArrayList<String>) :
    RecyclerView.Adapter<DayAdapter.DayViewHolder>() {

    val ROW = 6

    inner class DayViewHolder(val layout: View) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_day, parent, false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val day = holder.layout.findViewById<TextView>(R.id.fragment_calender_dayTv)
        day.text = dayList[position].date.toString()

        // 텍스트 색상 설정
        day.setTextColor(when (position % 7) {
            0 -> Color.RED
            6 -> Color.BLUE
            else -> Color.BLACK
        })

        // 비활성화 처리
        if (tmpMonth != dayList[position].month) {
            day.alpha = 0.4f
        }

        // 일정 카드뷰를 표시할 RecyclerView 추가
        val eventRv = holder.layout.findViewById<RecyclerView>(R.id.fragment_calendar_day_eventRv)
        val events = getEventsForDate(dayList[position]) // 해당 날짜의 이벤트 가져오기

        if (events.isNotEmpty()) {
            val eventAdapter = CalendarEventAdapter(events)
            eventRv.layoutManager = LinearLayoutManager(holder.layout.context)
            eventRv.adapter = eventAdapter
            eventRv.visibility = View.VISIBLE
        } else {
            eventRv.visibility = View.GONE
        }
    }

    private fun getEventsForDate(date: Date): List<Event> {
        val events = mutableListOf<Event>()

        // 예시 이벤트 추가 (시작일과 종료일)
        val startDate1 = Calendar.getInstance().apply { set(2025, 1, 30) }.time
        val endDate1 = Calendar.getInstance().apply { set(2025, 2, 2) }.time
        events.add(Event("일정 1", startDate1, endDate1))

        val startDate2 = Calendar.getInstance().apply { set(2025, 2, 3) }.time
        val endDate2 = Calendar.getInstance().apply { set(2025, 2, 5) }.time
        events.add(Event("일정 2", startDate2, endDate2))

        return events.filter { event ->
            event.startDate <= date && event.endDate >= date
        }
    }


    override fun getItemCount(): Int {
        return ROW * 7
    }
}

//
//// RecyclerView.Adapter 상속, 현재 월, 날짜 리스트, 일정 날짜 정보 받음
//class DayAdapter(val tmpMonth:Int, val dayList:MutableList<Date>, val date: ArrayList<String>) : RecyclerView.Adapter<DayAdapter.DayViewHolder>(){
//    // 최대 6주
//    val ROW = 6
//    private lateinit var binding: ItemCalendarDayBinding
//
//    inner class DayViewHolder(val layout: View) : RecyclerView.ViewHolder(layout)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
//        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_day,parent,false)
//        return DayViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
//        // textView를 현재 위치의 날짜 문자열로 변환
//        var day = holder.layout.findViewById<TextView>(R.id.fragment_calender_dayTv)
//        day.text = dayList[position].date.toString()
//        // 0: 일요일 - 빨간색, 6: 토요일 - 파랑색, 나머지 - 검정색
//        day.setTextColor(when(position%7){
//            0 -> Color.RED
//            6-> Color.BLUE
//            else -> Color.BLACK
//        })
//        // 현재 월에 해당하지 않으면 투명도 0.4f
//        if(tmpMonth != dayList[position].month){
//            day.alpha = 0.4f
//        }
//
//        val eventRv = holder.layout.findViewById<RecyclerView>(R.id.fragment_calendar_day_eventRv)
//        val events = getEventsForDate(dayList[position])
//
//        if(events.isNotEmpty()) {
//            val calendarEventAdapter = CalendarEventAdapter(events)
//            eventRv.layoutManager = LinearLayoutManager(holder.layout.context)
//            eventRv.adapter = calendarEventAdapter
//            eventRv.visibility = View.VISIBLE
//        } else {
//            eventRv.visibility = View.GONE
//        }
//
//        //추가적으로 일정이 있는지 확인하는 구간
//        for(i in 0..date.size-1){
//            // date에 있는 문자열을 잘라서 시작 월과 시작 일로 저장
//            var startmonth = date[i].substring(5,8).trim()
//            var startmonthOfday = date[i].substring(9,12).trim()
////            if(date[i].length > 9) {
////                var endmonth = date[i].substring(21, 24).trim()
////                var endmonth = date[i].substring(25, 28).trim()
////            }
//            // 현재 월과 현재 일 저장
//            var strMonth = (dayList[position].month+1).toString()
//            var strDay = day.text.toString()
//
//            // 월과 일이 한 자리 수인 경우 앞에 0을 추가하여 전부 2자리 수가 되도록 함
//            if(dayList[position].month.toString().length == 1){
//                strMonth = "0${strMonth}"
//            }
//            if(day.text.toString().length == 1){
//                strDay = "0${strDay}"
//            }
//
//            // 최종적으로 비교할 날짜 문자열 생성
//            var strDate = "${strMonth}월 ${strDay}일"
//            var comDate = "${startmonth}월 ${startmonthOfday}일"
//            // 현재 날짜 확인
//            var checkDay = day.text.toString()
//            if(checkDay.length == 1) {
//                checkDay = "0${checkDay}"
//            }
//
//            // 현재 날짜와 date의 일정 날짜가 일치할 경우 조건문 내부 코드 실행
//            if(checkDay.equals(strDay)){
//                if(strDate.equals(comDate)){
////                    holder.itemView.findViewById<CardView>(R.id.fragment_calendar_day_eventCv1).visibility = View.VISIBLE
////                    holder.itemView.findViewById<CardView>(R.id.fragment_calendar_day_eventCv2).visibility = View.VISIBLE
//                }
//            }
////
////            holder.itemView.findViewById<CardView>(R.id.fragment_calendar_day_eventCv1).setOnClickListener {
////
////            }
////            holder.itemView.findViewById<CardView>(R.id.fragment_calendar_day_eventCv1).setOnClickListener {
////
////            }
//
//
//        }
//    }
//
//
////    private fun ItemCalendarDayBinding.setOnSelected() {
////        itemLineMid1.visibility = View.VISIBLE
////        itemLineMid1.setBackgroundResource(R.drawable.task_line_circle)
////    }
////    private fun ItemCalendarDayBinding.setHead() {
////        itemLineMid1.visibility = View.VISIBLE
////        itemLineMid1.setBackgroundResource(R.drawable.task_line_start)
////    }
////    private fun ItemCalendarDayBinding.setMid() {
////        itemLineMid1.visibility = View.VISIBLE
////        itemLineMid1.setBackgroundResource(R.drawable.task_line_mid)
////    }
////    private fun ItemCalendarDayBinding.setTail() {
////        itemLineMid1.visibility = View.VISIBLE
////        itemLineMid1.setBackgroundResource(R.drawable.task_line_end)
////    }
////    private fun ItemCalendarDayBinding.reset() {
////        itemLineMid1.visibility = View.GONE
////    }
//
//    // 총 6주 7일 -> 42개의 날짜 아이템 반환
//    override fun getItemCount(): Int {
//        return ROW*7
//    }
//
//    private fun getEventsForDate(date: Date): List<Event> {
//        // 하드코딩된 이벤트 리스트 (실제 데이터는 DB나 API에서 가져오는 것이 좋음)
//        val events = listOf(
//            Event("일정 1", Date(2025, 1, 30), Date(2025, 2, 2)),
//            Event("일정 2", Date(2025, 2, 3), Date(2025, 2, 5))
//        )
//
//        // 해당 날짜에 맞는 이벤트 필터링
//        return events.filter { event ->
//            event.startDate <= date && event.endDate >= date
//        }
//    }
//
//
//
//}
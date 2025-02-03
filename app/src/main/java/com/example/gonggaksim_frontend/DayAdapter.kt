package com.example.gonggaksim_frontend

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.gonggaksim_frontend.databinding.ItemCalendarDayBinding
import java.util.*

class DayAdapter(val tmpMonth:Int, val dayList:MutableList<Date>, val date: ArrayList<String>) : RecyclerView.Adapter<DayAdapter.DayViewHolder>(){
    val ROW = 6
    private lateinit var binding: ItemCalendarDayBinding

    inner class DayViewHolder(val layout: View) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_day,parent,false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        var day = holder.layout.findViewById<TextView>(R.id.fragment_calender_dayTv)
        day.text = dayList[position].date.toString()
        day.setTextColor(when(position%7){
            0 -> Color.RED
            6-> Color.BLUE
            else -> Color.BLACK
        })
        if(tmpMonth != dayList[position].month){
            day.alpha = 0.4f
        }

        //추가적으로 일정이 있는지 확인하는 구간
        for(i in 0..date.size-1){
            var startmonth = date[i].substring(5,8).trim()
            var startmonthOfday = date[i].substring(9,12).trim()
//            if(date[i].length > 9) {
//                var endmonth = date[i].substring(21, 24).trim()
//                var endmonth = date[i].substring(25, 28).trim()
//            }
            var strMonth = (dayList[position].month+1).toString()
            var strDay = day.text.toString()

            if(dayList[position].month.toString().length == 1){
                strMonth = "0${strMonth}"
            }
            if(day.text.toString().length == 1){
                strDay = "0${strDay}"
            }
            var strDate = "${strMonth}월 ${strDay}일"
            var comDate = "${startmonth}월 ${startmonthOfday}일"
            var checkDay = day.text.toString()
            if(checkDay.length == 1) {
                checkDay = "0${checkDay}"
            }

            if(checkDay.equals(strDay)){
                if(strDate.equals(comDate)){
//                    holder.itemView.findViewById<CardView>(R.id.fragment_calendar_day_eventCv1).visibility = View.VISIBLE
//                    holder.itemView.findViewById<CardView>(R.id.fragment_calendar_day_eventCv2).visibility = View.VISIBLE
                }
            }
//
//            holder.itemView.findViewById<CardView>(R.id.fragment_calendar_day_eventCv1).setOnClickListener {
//
//            }
//            holder.itemView.findViewById<CardView>(R.id.fragment_calendar_day_eventCv1).setOnClickListener {
//
//            }


        }
    }


    private fun ItemCalendarDayBinding.setOnSelected() {
        itemLineMid1.visibility = View.VISIBLE
        itemLineMid1.setBackgroundResource(R.drawable.task_line_circle)
    }
    private fun ItemCalendarDayBinding.setHead() {
        itemLineMid1.visibility = View.VISIBLE
        itemLineMid1.setBackgroundResource(R.drawable.task_line_start)
    }
    private fun ItemCalendarDayBinding.setMid() {
        itemLineMid1.visibility = View.VISIBLE
        itemLineMid1.setBackgroundResource(R.drawable.task_line_mid)
    }
    private fun ItemCalendarDayBinding.setTail() {
        itemLineMid1.visibility = View.VISIBLE
        itemLineMid1.setBackgroundResource(R.drawable.task_line_end)
    }
    private fun ItemCalendarDayBinding.reset() {
        itemLineMid1.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        return ROW*7
    }


}
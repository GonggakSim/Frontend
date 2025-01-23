package com.example.gonggaksim_frontend

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class DayAdapter(val tmpMonth:Int, val dayList:MutableList<Date>, val date: ArrayList<String>) : RecyclerView.Adapter<DayAdapter.DayViewHolder>(){
    val ROW = 6
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
            var month = date[i].substring(5,8).trim()
            var monthOfday = date[i].substring(9,date[i].length-1).trim()

            var strMonth = (dayList[position].month+1).toString()
            var strDay = day.text.toString()

            if(dayList[position].month.toString().length == 1){
                strMonth = "0${strMonth}"
            }
            if(day.text.toString().length == 1){
                strDay = "0${strDay}"
            }
            var strDate = "${strMonth}월 ${strDay}일"
            var comDate = "${month}월 ${monthOfday}일"
            var checkDay = day.text.toString()
            if(checkDay.length == 1) {
                checkDay = "0${checkDay}"
            }

            if(checkDay.equals(strDay)){
                if(strDate.equals(comDate)){
                    holder.itemView.findViewById<ImageView>(R.id.fragment_calendar_point).visibility = View.VISIBLE
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return ROW*7
    }


}
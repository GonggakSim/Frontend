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
import java.util.*

class DayAdapter2(val tmpMonth:Int, val dayList:MutableList<Date>, val date: ArrayList<String>) : RecyclerView.Adapter<DayAdapter2.Day2ViewHolder>(){
    val ROW = 6
    inner class Day2ViewHolder(val layout: View) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Day2ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_day2,parent,false)
        return Day2ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Day2ViewHolder, position: Int) {
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
                    holder.itemView.findViewById<CardView>(R.id.fragment_calendar_day_eventCv1).visibility = View.VISIBLE
                    holder.itemView.findViewById<CardView>(R.id.fragment_calendar_day_eventCv2).visibility = View.VISIBLE
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return ROW*7
    }


}
package com.example.gonggaksim_frontend

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.util.*

class DayAdapter(val tmpMonth:Int, val dayList:MutableList<Date>, val date: ArrayList<String>) : RecyclerView.Adapter<DayAdapter.DayViewHolder>(){
    val ROW = 6
    inner class DayViewHolder(val layout: View) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_day,parent,false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        var day = holder.layout.findViewById<TextView>(R.id.fragment_calender_dayTv_1)
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
            var monthOfday = date[i].substring(9,12).trim()
            var monthText = date[i].substring(13,date[i].length).trim()

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

            val random = Random()
            val randomNum = random.nextInt(3)
            val list = mutableListOf<String>("A769F2", "6996F2", "F27969")

            if(checkDay.equals(strDay)){
                if(strDate.equals(comDate)){
                    holder.itemView.findViewById<CardView>(R.id.item_line).visibility = View.VISIBLE
                    holder.itemView.findViewById<TextView>(R.id.item_text_1).text = monthText
                    holder.itemView.findViewById<ImageView>(R.id.item_line_mid_1)
                        .setBackgroundColor(Color.parseColor("#${list[randomNum]}"))

                }
            }

        }
    }

//    private fun ItemCalendarDayBinding.setOnSelected() {
//        itemLineMid1.visibility = View.VISIBLE
//        itemLineMid1.setBackgroundResource(R.drawable.task_line_circle)
//    }
//    private fun ItemCalendarDayBinding.setHead() {
//        itemLineMid1.visibility = View.VISIBLE
//        itemLineMid1.setBackgroundResource(R.drawable.task_line_start)
//    }
//    private fun ItemCalendarDayBinding.setMid() {
//        itemLineMid1.visibility = View.VISIBLE
//        itemLineMid1.setBackgroundResource(R.drawable.task_line_mid)
//    }
//    private fun ItemCalendarDayBinding.setTail() {
//        itemLineMid1.visibility = View.VISIBLE
//        itemLineMid1.setBackgroundResource(R.drawable.task_line_end)
//    }
//    private fun ItemCalendarDayBinding.reset() {
//        itemLineMid1.visibility = View.GONE
//    }

    override fun getItemCount(): Int {
        return ROW*7
    }
}
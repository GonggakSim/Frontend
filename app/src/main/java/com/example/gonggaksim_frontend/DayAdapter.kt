package com.example.gonggaksim_frontend

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class DayAdapter(val tmpMonth:Int, val dayList:MutableList<Date>, val date: MutableList<String>) : RecyclerView.Adapter<DayAdapter.DayViewHolder>(){
    val ROW = 6

    inner class DayViewHolder(val layout: View) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_day,parent,false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        var day = holder.layout.findViewById<TextView>(R.id.fragment_calender_dayTv_1)
        day.text = dayList[position].date.toString()
        day.setTextColor(
            when (position % 7) {
                0 -> Color.RED
                6 -> Color.BLUE
                else -> Color.BLACK
            }
        )
        if (tmpMonth != dayList[position].month) {
            day.alpha = 0.4f
        }

        var eventIndex = -1

        //추가적으로 일정이 있는지 확인하는 구간
        for (i in 0..date.size - 1) {
            var month = date[i].substring(5, 8).trim()
            var monthOfday = date[i].substring(9, 12).trim()
            var monthText = date[i].substring(34, date[i].length).trim()

            var endMonth = date[i].substring(19, 22).trim()
            var endMonthOfday = date[i].substring(23, 26).trim()

            var strMonth = (dayList[position].month + 1).toString()
            var strDay = day.text.toString()

            if (dayList[position].month.toString().length == 1) {
                strMonth = "0${strMonth}"
            }
            if (day.text.toString().length == 1) {
                strDay = "0${strDay}"
            }
            var strDate = "${strMonth}월 ${strDay}일"
            var comDate = "${month}월 ${monthOfday}일"
            var endDate = "${endMonth}월 ${endMonthOfday}일"
            var checkDay = day.text.toString()
            if (checkDay.length == 1) {
                checkDay = "0${checkDay}"
            }

            var colorRandom = date[i].substring(27, 34).trim()

            if (checkDay.equals(strDay)) {
                if (strDate.equals(comDate)) {

                    eventIndex = i

                    holder.itemView.findViewById<CardView>(R.id.item_line).visibility = View.VISIBLE
                    holder.itemView.findViewById<TextView>(R.id.item_text_1).text = monthText
                    holder.itemView.findViewById<ImageView>(R.id.item_line_mid_1)
                        .setImageResource(R.drawable.task_line_start)
                    holder.itemView.findViewById<ImageView>(R.id.item_line_mid_1)
                        .setColorFilter(Color.parseColor("#$colorRandom"))

                    holder.itemView.findViewById<CardView>(R.id.item_line).setOnClickListener {
                        val dialog = DeletePopUp1(holder.itemView.context) { isConfirmed ->
                            if (isConfirmed && eventIndex != -1) {
                                date.removeAt(eventIndex) // 리스트에서 해당 일정 삭제
                                notifyDataSetChanged() // UI 갱신
                            }
                        }
                        dialog.show()
                    }
                    if(comDate == endDate) {

                        eventIndex = i
                        holder.itemView.findViewById<ImageView>(R.id.item_line_mid_1)
                            .setImageResource(R.drawable.task_line_circle)
                        holder.itemView.findViewById<ImageView>(R.id.item_line_mid_1)
                            .setColorFilter(Color.parseColor("#$colorRandom"))
                    }
                }

                if (strDate.equals(endDate) && comDate != endDate) {
                    eventIndex = i

                    holder.itemView.findViewById<CardView>(R.id.item_line).visibility = View.VISIBLE
                    holder.itemView.findViewById<TextView>(R.id.item_text_1).text = " "
                    holder.itemView.findViewById<ImageView>(R.id.item_line_mid_1)
                        .setImageResource(R.drawable.task_line_end)
                    holder.itemView.findViewById<ImageView>(R.id.item_line_mid_1)
                        .setColorFilter(Color.parseColor("#$colorRandom"))

                    holder.itemView.findViewById<CardView>(R.id.item_line).setOnClickListener {
                        val dialog = DeletePopUp1(holder.itemView.context) { isConfirmed ->
                            if (isConfirmed && eventIndex != -1) {
                                date.removeAt(eventIndex) // 리스트에서 해당 일정 삭제
                                notifyDataSetChanged() // UI 갱신
                            }
                        }
                        dialog.show()
                    }
                }
            }

            if (month == strMonth && endMonth > strMonth) {
                if (monthOfday.toInt() < strDay.toInt()) {

                    eventIndex = i
                    holder.itemView.findViewById<CardView>(R.id.item_line).visibility = View.VISIBLE
                    holder.itemView.findViewById<TextView>(R.id.item_text_1).text = " "
                    holder.itemView.findViewById<ImageView>(R.id.item_line_mid_1)
                        .setImageResource(R.drawable.task_line_mid)
                    holder.itemView.findViewById<ImageView>(R.id.item_line_mid_1)
                        .setColorFilter(Color.parseColor("#$colorRandom"))

                    holder.itemView.findViewById<CardView>(R.id.item_line).setOnClickListener {
                        val dialog = DeletePopUp1(holder.itemView.context) { isConfirmed ->
                            if (isConfirmed && eventIndex != -1) {
                                date.removeAt(eventIndex) // 리스트에서 해당 일정 삭제
                                notifyDataSetChanged() // UI 갱신
                            }
                        }
                        dialog.show()
                    }
                }
            }
            if (month < strMonth && endMonth == strMonth) {
                if (endMonthOfday.toInt() > strDay.toInt()) {
                    eventIndex = i
                    holder.itemView.findViewById<CardView>(R.id.item_line).visibility = View.VISIBLE
                    holder.itemView.findViewById<TextView>(R.id.item_text_1).text = " "
                    holder.itemView.findViewById<ImageView>(R.id.item_line_mid_1)
                        .setImageResource(R.drawable.task_line_mid)
                    holder.itemView.findViewById<ImageView>(R.id.item_line_mid_1)
                        .setColorFilter(Color.parseColor("#$colorRandom"))

                    holder.itemView.findViewById<CardView>(R.id.item_line).setOnClickListener {
                        val dialog = DeletePopUp1(holder.itemView.context) { isConfirmed ->
                            if (isConfirmed && eventIndex != -1) {
                                date.removeAt(eventIndex) // 리스트에서 해당 일정 삭제
                                notifyDataSetChanged() // UI 갱신
                            }
                        }
                        dialog.show()
                    }
                }
            }
            if (month == endMonth && month == strMonth) {
                if (monthOfday < strDay && endMonthOfday > strDay) {
                    if (monthOfday.toInt() < strDay.toInt()) {
                        eventIndex = i
                        holder.itemView.findViewById<CardView>(R.id.item_line).visibility =
                            View.VISIBLE
                        holder.itemView.findViewById<TextView>(R.id.item_text_1).text = " "
                        holder.itemView.findViewById<ImageView>(R.id.item_line_mid_1)
                            .setImageResource(R.drawable.task_line_mid)
                        holder.itemView.findViewById<ImageView>(R.id.item_line_mid_1)
                            .setColorFilter(Color.parseColor("#$colorRandom"))

                        holder.itemView.findViewById<CardView>(R.id.item_line).setOnClickListener {
                            val dialog = DeletePopUp1(holder.itemView.context) { isConfirmed ->
                                if (isConfirmed && eventIndex != -1) {
                                    date.removeAt(eventIndex) // 리스트에서 해당 일정 삭제
                                    notifyDataSetChanged() // UI 갱신
                                }
                            }
                            dialog.show()
                        }
                    }
                }
            }

            if (holder.itemView.findViewById<CardView>(R.id.item_line).visibility ==
                    View.VISIBLE) {
                if (tmpMonth != dayList[position].month) {
                    holder.itemView.findViewById<ImageView>(R.id.item_line_mid_1).alpha = 0.4f
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
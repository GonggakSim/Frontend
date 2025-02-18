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
import com.example.gonggaksim_frontend.databinding.ItemCalendarDay2Binding
import com.example.gonggaksim_frontend.databinding.ItemCalendarDayBinding
import java.util.*


class DayAdapter2(
    private val tmpMonth: Int,
    private val dayList: MutableList<Date>,
    private val date: ArrayList<String>,
    private val onDateRangeSelected: (Date, Date?) -> Unit
) : RecyclerView.Adapter<DayAdapter2.Day2ViewHolder>() {

    private var startDate: Date? = null
    private var endDate: Date? = null

    inner class Day2ViewHolder(val binding: ItemCalendarDay2Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val day = dayList[position]

            // 날짜 표시
            binding.fragmentCalenderDayTv.text = day.date.toString()
            binding.fragmentCalenderDayTv.setTextColor(
                when (position % 7) {
                    0 -> Color.RED
                    6 -> Color.BLUE
                    else -> Color.BLACK
                }
            )

            // 이전/다음 달 흐리게
            if (tmpMonth != day.month) {
                binding.fragmentCalenderDayTv.alpha = 0.4f
            }

            // 날짜 선택 로직
            binding.root.setOnClickListener {
                handleDateSelection(day)
            }

            // UI 업데이트
            updateDateUI(day)
        }

        private fun handleDateSelection(day: Date) {
            if (startDate == null || (startDate != null && endDate != null)) {
                startDate = day
                endDate = null
            } else if (endDate == null) {
                if (day.before(startDate)) {
                    endDate = startDate
                    startDate = day
                } else {
                    endDate = day
                }

                // 날짜 범위 전달
                onDateRangeSelected(startDate!!, endDate)
            }

            notifyDataSetChanged() // UI 갱신
        }

        private fun updateDateUI(day: Date) {
            binding.fragmentCalenderDayTv.setTextColor(Color.parseColor("#000000"))
            binding.itemLineMid12.visibility = View.GONE

            if (day == startDate) {
                binding.fragmentCalenderDayTv.setTextColor(Color.parseColor("#FFFFFF"))
                binding.itemLine12.visibility = View.VISIBLE
                if (endDate != null) {
                    binding.itemLineRight12.visibility = View.VISIBLE
                }
            } else if (day == endDate) {
                binding.fragmentCalenderDayTv.setTextColor(Color.parseColor("#FFFFFF"))
                binding.itemLine12.visibility = View.VISIBLE
                if (startDate != null) {
                    binding.itemLineLeft12.visibility = View.VISIBLE
                }
            } else if (startDate != null && endDate != null && day.after(startDate) && day.before(endDate)) {
                binding.fragmentCalenderDayTv.setTextColor(Color.parseColor("#FFFFFF"))
                binding.itemLineMid12.visibility = View.VISIBLE
            }

            if (startDate == endDate) {
                binding.itemLineRight12.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Day2ViewHolder {
        val binding = ItemCalendarDay2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Day2ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: Day2ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = 6 * 7

    fun getSelectedDates(): Pair<Date?, Date?> {
        return Pair(startDate, endDate)
    }
}

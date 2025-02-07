package com.example.gonggaksim_frontend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
class CalendarEventAdapter(private val eventList: List<Event>) : RecyclerView.Adapter<CalendarEventAdapter.EventViewHolder>() {

    inner class EventViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event_calendar, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]

        // 카드뷰 배경 설정
        when {
            position == 0 -> {
                holder.view.background = ContextCompat.getDrawable(holder.view.context, R.drawable.rounded_left)
                holder.view.findViewById<TextView>(R.id.event_text).text = event.title
            }
            position == eventList.size - 1 -> {
                holder.view.background = ContextCompat.getDrawable(holder.view.context, R.drawable.rounded_right)
                holder.view.findViewById<TextView>(R.id.event_text).text = ""
            }
            else -> {
                holder.view.background = ContextCompat.getDrawable(holder.view.context, R.drawable.rounded_square)
                holder.view.findViewById<TextView>(R.id.event_text).text = ""
            }
        }

        // 전체 일정 기간 중간에만 텍스트 표시
        if (position == eventList.size / 2) {
            holder.view.findViewById<TextView>(R.id.event_text).text = event.title
        } else {
            holder.view.findViewById<TextView>(R.id.event_text).visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = eventList.size
}


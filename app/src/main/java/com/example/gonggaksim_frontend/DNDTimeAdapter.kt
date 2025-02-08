package com.example.gonggaksim_frontend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DNDTimeAdapter(private val dndTimeList: MutableList<DNDTime>) :
    RecyclerView.Adapter<DNDTimeAdapter.DNDTimeViewHolder>() {

    inner class DNDTimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewDays: TextView = itemView.findViewById(R.id.textViewDays)
        private val textViewTime: TextView = itemView.findViewById(R.id.textViewTime)
        private val btnDelete: ImageView = itemView.findViewById(R.id.btnDelete)

        fun bind(dndTime: DNDTime, position: Int) {
            textViewDays.text = dndTime.days.joinToString(", ") // 요일 리스트 → "월, 화, 수"
            textViewTime.text = "${dndTime.startTime} - ${dndTime.endTime}"

            // 삭제 버튼 클릭 시 해당 아이템 삭제
            btnDelete.setOnClickListener {
                dndTimeList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, dndTimeList.size)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DNDTimeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dnd_time, parent, false) // 새로운 아이템 레이아웃 적용
        return DNDTimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: DNDTimeViewHolder, position: Int) {
        holder.bind(dndTimeList[position], position)
    }

    override fun getItemCount(): Int = dndTimeList.size
}
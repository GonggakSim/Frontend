package com.example.gonggaksim_frontend

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.gonggaksim_frontend.databinding.ItemScheduleButtonBinding

class ExamScheduleAdapter(
    private val onSelectionChanged: (Boolean) -> Unit
) : RecyclerView.Adapter<ExamScheduleAdapter.ExamScheduleViewHolder>() {

    private val scheduleList = mutableListOf<String>()
    private var selectedPosition: Int = RecyclerView.NO_POSITION // 선택된 버튼 위치

    fun submitList(data: List<String>) {
        scheduleList.clear()
        scheduleList.addAll(data)
        notifyDataSetChanged()
    }

    fun getSelectedButtonText(): String? {
        return if (selectedPosition != RecyclerView.NO_POSITION) {
            scheduleList[selectedPosition]
        } else {
            null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamScheduleViewHolder {
        val binding = ItemScheduleButtonBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ExamScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExamScheduleViewHolder, position: Int) {
        holder.bind(scheduleList[position], position)
    }

    override fun getItemCount(): Int = scheduleList.size

    inner class ExamScheduleViewHolder(private val binding: ItemScheduleButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(schedule: String, position: Int) {
            binding.scheduleButton.text = schedule

            // 버튼 선택 상태 설정
            binding.scheduleButton.isSelected = position == selectedPosition

            // 선택 상태에 따른 배경 및 텍스트 색상 적용
            binding.scheduleButton.setBackgroundResource(
                if (binding.scheduleButton.isSelected) R.drawable.btn_schedule_selected else R.drawable.btn_schedule
            )
            binding.scheduleButton.setTextColor(
                if (binding.scheduleButton.isSelected) binding.scheduleButton.context.getColor(R.color.main_01)
                else binding.scheduleButton.context.getColor(R.color.grayscale_10)
            )

            // 버튼 클릭 리스너
            binding.scheduleButton.setOnClickListener {
                if (selectedPosition == position) {
                    // 동일한 버튼 클릭 시 선택 해제
                    selectedPosition = RecyclerView.NO_POSITION
                    notifyItemChanged(position)
                    onSelectionChanged(false) //일정 추가하기 버튼 숨김
                } else {
                    // 이전 선택된 버튼 초기화
                    val previousPosition = selectedPosition
                    selectedPosition = position
                    if (previousPosition != RecyclerView.NO_POSITION) {
                        notifyItemChanged(previousPosition)
                    }

                    // 새로 선택된 버튼 갱신
                    notifyItemChanged(selectedPosition)
                    onSelectionChanged(true) //일정 추가하기 버튼 보이기
                }
            }
        }
    }
}

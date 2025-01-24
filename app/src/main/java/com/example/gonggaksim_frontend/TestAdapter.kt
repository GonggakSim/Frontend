package com.example.gonggaksim_frontend

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TestAdapter(
    private val context: Context,
    private val items: List<String>,
    private val onItemClick: (String) -> Unit // 클릭 이벤트를 전달받을 람다 함수 추가
) : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {

    inner class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text_view)
        val arrowIcon: ImageView = itemView.findViewById(R.id.arrow_icon)

        init {
            itemView.setOnClickListener {
                val examName = items[adapterPosition]
                onItemClick(examName) // 클릭된 시험 이름을 전달
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_test, parent, false)
        return TestViewHolder(view)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.textView.text = items[position]
    }

    override fun getItemCount(): Int = items.size
}

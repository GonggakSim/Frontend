package com.example.gonggaksim_frontend

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TestAdapter(
    private val context: Context,
    private val itemList: List<Certification>,  // List<String> 대신 List<Certification>
    private val onItemClicked: (Int) -> Unit // certification_id를 직접 전달
) : RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.text_view)

        fun bind(item: Certification) {
            nameTextView.text = item.name
            itemView.setOnClickListener {
                onItemClicked(item.certificationId) // 클릭 이벤트 발생 시 콜백 호출
                Log.d("certiid", item.certificationId.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_test, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])  // Certification 객체에서 name만 표시
    }

    override fun getItemCount(): Int = itemList.size
}

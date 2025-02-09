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
    private val items: List<Certification>,  // List<String> 대신 List<Certification>
    private val onItemClick: (Certification) -> Unit
) : RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.text_view)

        fun bind(name: String) {
            nameTextView.text = name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_test, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position].name)  // Certification 객체에서 name만 표시
    }

    override fun getItemCount(): Int = items.size
}

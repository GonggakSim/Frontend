package com.example.gonggaksim_frontend

import android.app.Activity
import android.os.Bundle
import com.example.gonggaksim_frontend.databinding.ActivityExamSuggestionBinding

class ExamSuggestionActivity : Activity() {
    private lateinit var binding: ActivityExamSuggestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExamSuggestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickButtonEvent()
    }

    private fun clickButtonEvent() {
        binding.esfSuggestString.setOnClickListener {

        }

        binding.esfDateString.setOnClickListener {

        }

        binding.esfBtn.setOnClickListener {

        }
    }
}
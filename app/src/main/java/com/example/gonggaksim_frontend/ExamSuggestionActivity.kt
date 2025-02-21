package com.example.gonggaksim_frontend

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.gonggaksim_frontend.api.ExamRecommendationRequest
import com.example.gonggaksim_frontend.api.ExamRecommendationResponse
import com.example.gonggaksim_frontend.databinding.ActivityExamSuggestionBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExamSuggestionActivity : Activity() {
    private lateinit var binding: ActivityExamSuggestionBinding

    private lateinit var token: String

    private var name: String = ""
    private var studyExperience: String = ""
    private var studyTimePerDay: String = ""
    private var studyFrequency: String = ""
    private var examGoal: String = ""

    private var suggestion: String = ""
    private var suggestionText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExamSuggestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("token")) {
            token = intent.getStringExtra("token")!!
            name = intent.getStringExtra("name")!!
            studyExperience = intent.getStringExtra("studyExperience")!!
            studyTimePerDay = intent.getStringExtra("studyTimePerDay")!!
            studyFrequency = intent.getStringExtra("studyFrequency")!!
            examGoal = intent.getStringExtra("examGoal")!!
            suggestionText = intent.getStringExtra("text")!!
            suggestion = intent.getStringExtra("date")!!
        }

//        binding.esfDateString.text = suggestion
//        binding.esfSuggestString.text = suggestionText

        clickButtonEvent()
    }


    private fun clickButtonEvent() {
        binding.esfSuggestString.setOnClickListener {

        }

        binding.esfDateString.setOnClickListener {

        }

        binding.esfBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            finishAffinity()
            startActivity(intent)
        }
    }
}
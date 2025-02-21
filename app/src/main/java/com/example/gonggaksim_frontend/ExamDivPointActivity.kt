package com.example.gonggaksim_frontend

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.gonggaksim_frontend.api.ExamRecommendationRequest
import com.example.gonggaksim_frontend.api.ExamRecommendationResponse
import com.example.gonggaksim_frontend.databinding.ActivityExamDivPointBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExamDivPointActivity : Activity() {
    private lateinit var binding: ActivityExamDivPointBinding
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

        if (intent.hasExtra("token")) {
            token = intent.getStringExtra("token")!!
        }

        binding = ActivityExamDivPointBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestExamRecommendation()

        clickButtonEvent()
    }

    private fun requestExamRecommendation() {
        val token : String = token
        val request = ExamRecommendationRequest(
            userId = 24, // 실제 userId 가져오기
            name = name,
            studyExperience = studyExperience,
            studyTimePerDay = studyTimePerDay,
            studyFrequency = studyFrequency,
            examGoal = examGoal
        )

        RetrofitClient.scheduleApi.getExamRecommendation(authToken = "Bearer ${token}", provider = "", request).enqueue(object :
            Callback<ExamRecommendationResponse> {
            override fun onResponse(call: Call<ExamRecommendationResponse>, response: Response<ExamRecommendationResponse>) {
                if (response.isSuccessful) {
                    Log.d("AiExam", "AI 시험 추천 연결 성공")
                    val data = response.body()
                    if (data?.status == "success") {
                        Log.d("AiExam", "AI 시험 추천 바디 있음 ${response.body()}")

                        suggestionText = response.body()!!.data?.recommendedPlan.toString()

                        val monthText = response.body()!!.data?.examDate.toString().substring(4, 7).trim()
                        val dayText = response.body()!!.data?.examDate.toString().substring(7, response.body()!!.data?.examDate.toString().length).trim()

                        suggestion = "${monthText}월 ${dayText}일"

                    } else {
                        Log.e("AiExam", "AI 시험 추천 바디 없음")
                    }
                } else {
                    Log.e("AiExam", "서버 응답 오류")
                }
            }

            override fun onFailure(call: Call<ExamRecommendationResponse>, t: Throwable) {
                Log.e("AiExam", "서버 연결 안됨")
            }
        })
    }



    private fun clickButtonEvent() {
        // 숙련 정도 버튼 처리
        binding.zero.setOnClickListener {
            binding.zero.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.one.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.two.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.three.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.four.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))

            studyExperience = binding.zeroTv.text.toString()

            binding.zeroTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.oneTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.twoTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.threeTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))

        }
        binding.one.setOnClickListener {
            binding.zero.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.one.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.two.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.three.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.four.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))

            studyExperience = binding.oneTv.text.toString()

            binding.zeroTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.oneTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.twoTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.threeTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
        }
        binding.two.setOnClickListener {
            binding.zero.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.one.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.two.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.three.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.four.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))

            studyExperience = binding.twoTv.text.toString()

            binding.zeroTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.oneTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.twoTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.threeTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
        }
        binding.three.setOnClickListener {
            binding.zero.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.one.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.two.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.three.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.four.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))

            studyExperience = binding.threeTv.text.toString()

            binding.zeroTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.oneTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.twoTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.threeTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.fourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
        }
        binding.four.setOnClickListener {
            binding.zero.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.one.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.two.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.three.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.four.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))

            studyExperience = binding.fourTv.text.toString()

            binding.zeroTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.oneTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.twoTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.threeTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
        }

        // 하루 공부 가능 시간 버튼 처리
        binding.zeroHour.setOnClickListener {
            binding.zeroHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.oneHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.twoHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.threeHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fourHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fiveHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))

            studyTimePerDay = binding.zeroHourTv.text.toString()

            binding.zeroHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.oneHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.twoHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.threeHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fourHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fiveHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
        }
        binding.oneHour.setOnClickListener {
            binding.zeroHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.oneHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.twoHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.threeHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fourHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fiveHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))

            studyTimePerDay = binding.oneHourTv.text.toString()

            binding.zeroHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.oneHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.twoHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.threeHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fourHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fiveHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
        }
        binding.twoHour.setOnClickListener {
            binding.zeroHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.oneHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.twoHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.threeHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fourHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fiveHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))

            studyTimePerDay = binding.twoHourTv.text.toString()

            binding.zeroHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.oneHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.twoHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.threeHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fourHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fiveHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
        }
        binding.threeHour.setOnClickListener {
            binding.zeroHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.oneHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.twoHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.threeHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.fourHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fiveHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))

            studyTimePerDay = binding.threeHourTv.text.toString()

            binding.zeroHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.oneHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.twoHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.threeHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.fourHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fiveHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
        }
        binding.fourHour.setOnClickListener {
            binding.zeroHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.oneHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.twoHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.threeHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fourHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.fiveHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))

            studyTimePerDay = binding.fourHourTv.text.toString()

            binding.zeroHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.oneHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.twoHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.threeHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fourHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.fiveHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
        }
        binding.fiveHour.setOnClickListener {
            binding.zeroHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.oneHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.twoHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.threeHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fourHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fiveHour.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))

            studyTimePerDay = binding.fiveHourTv.text.toString()

            binding.zeroHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.oneHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.twoHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.threeHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fourHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.fiveHourTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
        }

        // 하루 공부 가능 양 버튼 처리
        binding.dayAll.setOnClickListener {
            binding.dayAll.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.dayFree.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.dayWeekend.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))

            studyFrequency = binding.dayAllTv.text.toString()

            binding.dayAllTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.dayFreeTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.dayWeekendTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
        }
        binding.dayFree.setOnClickListener {
            binding.dayAll.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.dayFree.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.dayWeekend.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))

            studyFrequency = binding.dayFreeTv.text.toString()

            binding.dayAllTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.dayFreeTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.dayWeekendTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
        }
        binding.dayWeekend.setOnClickListener {
            binding.dayAll.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.dayFree.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.dayWeekend.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))

            studyFrequency = binding.dayWeekendTv.text.toString()

            binding.dayAllTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.dayFreeTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.dayWeekendTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
        }

        // 시험 목표 처리
        binding.six.setOnClickListener {
            binding.six.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.seven.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.eight.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.eightHalf.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.nine.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.nineHalf.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))

            examGoal = binding.sixTv.text.toString()

            binding.sixTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.sevenTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.eightTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.eightHalfTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.nineTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.nineHalfTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
        }
        binding.seven.setOnClickListener {
            binding.six.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.seven.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.eight.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.eightHalf.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.nine.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.nineHalf.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))

            examGoal = binding.sevenTv.text.toString()

            binding.sixTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.sevenTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.eightTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.eightHalfTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.nineTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.nineHalfTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
        }
        binding.eight.setOnClickListener {
            binding.six.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.seven.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.eight.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.eightHalf.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.nine.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.nineHalf.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))

            examGoal = binding.eightTv.text.toString()

            binding.sixTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.sevenTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.eightTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.eightHalfTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.nineTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.nineHalfTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
        }
        binding.eightHalf.setOnClickListener {
            binding.six.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.seven.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.eight.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.eightHalf.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.nine.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.nineHalf.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))

            examGoal = binding.eightHalfTv.text.toString()

            binding.sixTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.sevenTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.eightTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.eightHalfTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.nineTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.nineHalfTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
        }
        binding.nine.setOnClickListener {
            binding.six.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.seven.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.eight.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.eightHalf.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.nine.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.nineHalf.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))

            examGoal = binding.nineTv.text.toString()

            binding.sixTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.sevenTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.eightTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.eightHalfTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.nineTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.nineHalfTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
        }
        binding.nineHalf.setOnClickListener {
            binding.six.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.seven.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.eight.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.eightHalf.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.nine.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.nineHalf.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))

            examGoal = binding.nineHalfTv.text.toString()

            binding.sixTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.sevenTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.eightTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.eightHalfTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.nineTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.nineHalfTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
        }

        // 뒤로가기 버튼 처리
        binding.edpfBackBtn.setOnClickListener {

        }

        // 다음 버튼 처리
        binding.edpfBtn.setOnClickListener {
            val intent = Intent(this, ExamSuggestionActivity::class.java)
            intent.apply{
                putExtra("token", token)
                putExtra("name", name)
                putExtra("studyExperience", studyExperience)
                putExtra("studyTimePerDay", studyTimePerDay)
                putExtra("studyFrequency", studyFrequency)
                putExtra("examGoal", examGoal)

                putExtra("text", suggestionText)
                putExtra("date", suggestion)
            }
            finishAffinity()
            startActivity(intent)
        }
    }
}
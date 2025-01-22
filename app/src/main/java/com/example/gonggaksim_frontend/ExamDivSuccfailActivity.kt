package com.example.gonggaksim_frontend

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.gonggaksim_frontend.databinding.ActivityExamDivSuccfailBinding

class ExamDivSuccfailActivity : Activity() {
    private lateinit var binding: ActivityExamDivSuccfailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExamDivSuccfailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickButtonEvent()
    }

    private fun clickButtonEvent() {
        // 숙련 정도 버튼 처리
        binding.zero.setOnClickListener {
            binding.zero.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.one.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.two.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.three.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.four.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))

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

            binding.dayAllTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.dayFreeTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.dayWeekendTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
        }
        binding.dayFree.setOnClickListener {
            binding.dayAll.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.dayFree.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.dayWeekend.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))

            binding.dayAllTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.dayFreeTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
            binding.dayWeekendTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
        }
        binding.dayWeekend.setOnClickListener {
            binding.dayAll.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.dayFree.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.dayWeekend.setStrokeColor(ContextCompat.getColor(this, R.color.color_ggs_blue))

            binding.dayAllTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.dayFreeTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_gray))
            binding.dayWeekendTv.setTextColor(ContextCompat.getColor(this, R.color.color_ggs_blue))
        }

        // 뒤로가기 버튼 처리
        binding.edsfBackBtn.setOnClickListener {

        }

        // 다음 버튼 처리
        binding.edsfBtn.setOnClickListener {
            val intent = Intent(this, ExamSuggestionActivity::class.java)
            startActivity(intent)
        }
    }
}
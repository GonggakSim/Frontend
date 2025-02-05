package com.example.gonggaksim_frontend

import java.time.LocalDate

data class DayItem(
    val date: LocalDate,
    val schedules: List<Schedule>
)

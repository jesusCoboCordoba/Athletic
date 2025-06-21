package com.politecnico.athleticapp.ui.models

data class DailyWorkout(
    val day: String,
    val workoutType: String,
    val dayNumber: Int,
    var isCompleted: Boolean = false
) 
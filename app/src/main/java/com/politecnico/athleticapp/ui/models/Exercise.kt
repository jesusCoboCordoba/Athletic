package com.politecnico.athleticapp.ui.models

import androidx.annotation.DrawableRes

data class Exercise(
    val name: String,
    val setsAndReps: String,
    @DrawableRes val imageResId: Int,
    val description: String = "Default description. Replace with actual content."
) 
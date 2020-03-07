package com.example.bmicalculator.ui.home

import com.example.bmicalculator.R

enum class BMI(val colour: Int, val workout: Int, val desc: Int) {
    UNDERWEIGHT(R.color.bmi_underweight, R.string.bmi_underweight_workout, R.string.bmi_underweight_desc),
    NORMAL(R.color.bmi_normal, R.string.bmi_normal_workout, R.string.bmi_normal_workout),
    OVERWEIGHT(R.color.bmi_overweight, R.string.bmi_overweight_workout, R.string.bmi_overweight_desc),
    OBESE(R.color.bmi_obese, R.string.bmi_obese_workout, R.string.bmi_obese_desc),
    EXTREMELY_OBESE(R.color.bmi_extremely_obese, R.string.bmi_extremely_obese_workout, R.string.bmi_extremely_obese_desc)
}
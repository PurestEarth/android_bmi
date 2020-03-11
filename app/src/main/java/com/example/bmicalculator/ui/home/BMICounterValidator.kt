package com.example.bmicalculator

import android.util.Log
import kotlin.math.pow

class BMICounterValidator{

    fun getBMIMetric(weight: Double, height: Double): Double{
        return weight/ (height * 0.01).pow(2)
    }

    fun getBMIImperial(weight: Double, feet: Double, inches: Double): Double{
        return weight / (inches + 12*feet).pow(2) * 703
    }

}
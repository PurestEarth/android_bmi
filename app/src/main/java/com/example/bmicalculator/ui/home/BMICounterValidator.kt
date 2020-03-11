package com.example.bmicalculator.ui.home

import android.util.Log
import kotlin.math.pow

class BMICounterValidator{

    fun getBMIMetric(weight: Double, height: Double): Double{
            Log.i("BENIZ", "$weight $height")
            return weight/ (height * 0.01).pow(2)
        return 0.0
    }

    fun getBMIImperial(weight: Double, feet: Double, inches: Double): Double{
            Log.i("BENIZ", "$weight $feet, $inches")
            return weight / (inches + 12*feet).pow(2) * 703

        return 0.0
    }

}
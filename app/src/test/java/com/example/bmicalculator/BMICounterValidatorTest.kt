package com.example.bmicalculator

import com.example.bmicalculator.ui.home.BMI
import com.example.bmicalculator.ui.home.HomeFragment
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.RoundingMode
import java.text.DecimalFormat

class BMICounterValidatorTest{

    @Test
    fun getBMIMetricTest() {
        val bmiCV = BMICounterValidator()
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        assertTrue(df.format(bmiCV.getBMIMetric(65.0,180.0)).toDouble() == 20.07)
        assert(df.format(bmiCV.getBMIMetric(120.0,180.0)).toDouble() == 37.04)
        assert(df.format(bmiCV.getBMIMetric(100.0,180.0)).toDouble() == 30.87)
        assert(df.format(bmiCV.getBMIMetric(2488.0,12.0)).toDouble() == 172777.78)
    }

    @Test
    fun getBMIImperialTest(){
        val bmiCV = BMICounterValidator()
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        assert(df.format(bmiCV.getBMIImperial(143.3,5.0, 11.0)).toDouble() == 19.99)
        assert(df.format(bmiCV.getBMIImperial(183.3,5.0, 11.0)).toDouble() == 25.57)
        assert(df.format(bmiCV.getBMIImperial(183.3,7.0, 11.0)).toDouble() == 14.28)
        assert(df.format(bmiCV.getBMIImperial(220.3,4.0, 2.0)).toDouble() == 61.95)
    }

}
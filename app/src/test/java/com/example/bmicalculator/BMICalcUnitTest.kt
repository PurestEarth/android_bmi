package com.example.bmicalculator


import com.example.bmicalculator.ui.home.BMI
import com.example.bmicalculator.ui.home.HomeFragment
import org.junit.Test

class BMICalcUnitTest{

    @Test
    fun getBMIMetricTest(){
        val homeFrag = HomeFragment()

        /*assert(homeFrag.getBMIMetric(65.0,180.0) == 20.07)
        assert(homeFrag.getBMIMetric(120.0,180.0) == 37.04)
        assert(homeFrag.getBMIMetric(100.0,180.0) == 30.87)
        assert(homeFrag.getBMIMetric(2488.0,12.0) == 172777.78)*/
    }
    @Test
    fun getBMIImperialTest(){
        val homeFrag = HomeFragment()
        /*assert(homeFrag.getBMIImperial(143.3,5.0, 11.0) == 19.95)
        assert(homeFrag.getBMIImperial(183.3,5.0, 11.0) == 25.53)
        assert(homeFrag.getBMIImperial(183.3,7.0, 11.0) == 14.26)
        assert(homeFrag.getBMIImperial(220.3,4.0, 2.0) == 61.87)*/
    }

    @Test
    fun getColorForBMITest(){
        val homeFrag = HomeFragment()
        assert(homeFrag.getColorForBMI(61.87) == BMI.EXTREMELY_OBESE.colour)
        assert(homeFrag.getColorForBMI(14.26) == BMI.UNDERWEIGHT.colour)
        assert(homeFrag.getColorForBMI(23.53) == BMI.NORMAL.colour)
        assert(homeFrag.getColorForBMI(26.87) == BMI.OVERWEIGHT.colour)
        assert(homeFrag.getColorForBMI(30.87) == BMI.OBESE.colour)
    }


}
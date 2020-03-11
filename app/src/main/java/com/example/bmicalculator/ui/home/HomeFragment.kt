package com.example.bmicalculator.ui.home

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.ColorInt
import androidx.core.animation.doOnEnd
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.bmicalculator.BMICounterValidator
import com.example.bmicalculator.R
import com.example.bmicalculator.ui.settings.SettingsViewModel
import org.w3c.dom.Text
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.pow
import kotlin.math.round


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var bmiCounterValidator: BMICounterValidator
    private lateinit var errorMessage : String
    var height: Double = 0.0
    var feet: Double = 0.0
    var inches: Double = 0.0
    var weight: Double = 0.0
    var imperials: Boolean = false
    private var bmi: Double = 0.0
    var bmiEn = BMI.UNDERWEIGHT
    val bmiDiv: Map<Double, BMI> = mapOf(
        18.5 to BMI.UNDERWEIGHT,
        24.9 to BMI.NORMAL,
        29.9 to BMI.OVERWEIGHT,
        34.9 to BMI.OBESE)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        bmiCounterValidator =  BMICounterValidator()
        val weightInput: EditText = root.findViewById(R.id.editText2)
        val heightInput: EditText = root.findViewById(R.id.editText3)
        val heightInputFt: EditText = root.findViewById(R.id.editText)
        val heightInputInch: EditText = root.findViewById(R.id.editText4)
        val bmiOut: TextView = root.findViewById(R.id.textView1)
        val countBmiButton: Button = root.findViewById(R.id.button)
        val settingsViewModel = ViewModelProvider(this.requireActivity()).get(SettingsViewModel::class.java)
        if(imperials != settingsViewModel.getImperial()){
            imperials = settingsViewModel.getImperial()
            if (imperials){
                weightInput.setHint(R.string.weight_hint_lb)
                heightInput.isInvisible = true
                heightInputFt.isInvisible = false
                heightInputInch.isInvisible = false
            }
            else{
                heightInput.setText(R.string.height_hint_cm)
                weightInput.setText(R.string.weight_hint_kg)
                heightInput.isInvisible = false
                heightInputFt.isInvisible = true
                heightInputInch.isInvisible = true
            }
        }
        weightInput.addTextChangedListener {
            if(it.toString().isNotEmpty()){
                try{
                    weight = it.toString().toDouble()
                } catch (e: NumberFormatException){
                    Toast.makeText(weightInput.context, this.context?.resources?.getString(R.string.parsing_broken).toString(), Toast.LENGTH_SHORT).show()
                }

            }
            else{
                weight = 0.0
            }
        }
        heightInput.addTextChangedListener {
            if(it.toString().isNotEmpty()) {
                try {
                    height = it.toString().toDouble()
                } catch (e: NumberFormatException){
                    Toast.makeText(weightInput.context, this.context?.resources?.getString(R.string.parsing_broken).toString(), Toast.LENGTH_SHORT).show()
                }
            }
            else{
                height = 0.0
            }
        }
        heightInputFt.addTextChangedListener{
            if(it.toString().isNotEmpty()) {
                try {
                    feet = it.toString().toDouble()
                } catch (e: NumberFormatException){
                    Toast.makeText(weightInput.context, this.context?.resources?.getString(R.string.parsing_broken).toString(), Toast.LENGTH_SHORT).show()
                }
            }
            else{
                feet = 0.0
            }
        }
        heightInputInch.addTextChangedListener{
            if(it.toString().isNotEmpty()) {
                try {
                    inches = it.toString().toDouble()
                } catch (e: NumberFormatException){
                    Toast.makeText(weightInput.context, this.context?.resources?.getString(R.string.parsing_broken).toString(), Toast.LENGTH_SHORT).show()
                }
            }else {
                inches = 0.0
            }

        }
        countBmiButton.setOnClickListener {
            hideKeyboard()
            setBMI(bmiOut)
        }
        bmiOut.setOnClickListener(){
            if((bmiOut.text as String) != this.context?.resources?.getString(R.string.bmi_hint)){
                displayPopup(inflater, container)
            }
        }
        return root
    }

    private fun displayPopup(inflater: LayoutInflater, container: ViewGroup?){
        val popupView = inflater.inflate(R.layout.popup, container, false)
        val dm = context?.resources?.displayMetrics
        val popupWindow = PopupWindow(popupView,
            dm?.widthPixels?.times(0.8)?.toInt()!!,
            dm?.heightPixels?.times(0.8)?.toInt()!!, true)

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        val popupBMI: TextView = popupView.findViewById(R.id.textView4)
        val popupWorkout: TextView = popupView.findViewById(R.id.textView5)
        val popupDesc: TextView = popupView.findViewById(R.id.textView6)
        setProperContent(popupWorkout, popupDesc)
        popupBMI.text = bmi.toString()
        popupView.setOnTouchListener { _, _ ->
            popupWindow.dismiss()
            true
        }
    }

    private fun setProperContent(popupWorkout: TextView, popupDesc: TextView) {
        popupWorkout.text = resources.getText(bmiEn.workout)
        popupDesc.text = resources.getText(bmiEn.desc)
    }

    private fun setBMI(view: TextView){
        if(validate()) {
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            bmi = if (imperials) df.format(bmiCounterValidator.getBMIImperial(weight, feet, inches)).toDouble() else df.format(bmiCounterValidator.getBMIMetric(weight,height)).toDouble()
            startCountAnimation(view, bmi)
        }
        else{
            Toast.makeText(view.context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun validate(): Boolean{
        return if(imperials){
            return if(feet <= 0.0){
                errorMessage = this.context?.resources?.getString(R.string.error_no_height_feet).toString()
                return false
            } else if (weight <= 0.0){
                errorMessage = this.context?.resources?.getString(R.string.error_no_weight).toString()
                return false
            }
            else if (inches <= 0.0){
                errorMessage = this.context?.resources?.getString(R.string.error_no_height_inches).toString()
                return false
            }else true
        } else{
            return if(height <= 0.0){
                errorMessage = this.context?.resources?.getString(R.string.error_no_height).toString()
                return false
            } else if (weight <= 0.0){
                errorMessage = this.context?.resources?.getString(R.string.error_no_weight).toString()
                return false
            } else true
        }
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun startCountAnimation(textView: TextView, max: Double) {
        val animator = ValueAnimator.ofInt(0, max.toInt())
        animator.duration = 2000
        animator.addUpdateListener { animation -> textView.text = animation.animatedValue.toString() }

        animator.start()
        animator.doOnEnd {
            textView.text = max.toString()

            getColorForBMI(bmi)?.let { it1 -> textView.setTextColor(it1) }

        }
    }

    fun getColorForBMI(bmi: Double): Int? {
        for (key in bmiDiv.keys){
            if (bmi <= key){
                bmiEn = bmiDiv[key] ?: error(" Something's gone awfully wrong ")
                return bmiDiv[key]?.let { resources.getColor(it.colour) }
            }
        }
        return resources.getColor(BMI.EXTREMELY_OBESE.colour)
    }
}

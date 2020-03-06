package com.example.bmicalculator.ui.home

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.bmicalculator.R
import com.example.bmicalculator.ui.settings.SettingsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text
import kotlin.math.pow
import kotlin.math.roundToInt

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    var height: Double = 0.0
    var weight: Double = 0.0
    var imperials: Boolean = false
    private lateinit var bmi: String
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

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
                weightInput.setText(R.string.weight_hint_lb)
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
        //TODO secure from too big values

        weightInput.addTextChangedListener {
            weight = it.toString().toDouble()
        }
        heightInput.addTextChangedListener {
            height = it.toString().toDouble()
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
        popupBMI.text = bmi
        popupView.setOnTouchListener { _, _ ->
            popupWindow.dismiss()
            true
        }
    }

    private fun setBMI(view: TextView){
        //todo check setting || validate
        if(weight > 0 && height > 0) {
            view.text = getBMIMetric(weight,height).toString()
            bmi = view.text.toString()
        }
    }

    fun getBMIMetric(weight: Double, height: Double): Double{
        if( height > 0){
            return weight/ (height * 0.01).pow(2)
        }
        return 0.0
    }

    fun getBMIImperial(weight: Double, height: Double): Double{
        // todo check how the fuck count it using feet and inches
        if( height > 0) {
            return weight / height.pow(2) * 703
        }
        return 0.0
    }
    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

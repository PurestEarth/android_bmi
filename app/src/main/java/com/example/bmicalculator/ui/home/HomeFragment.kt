package com.example.bmicalculator.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.bmicalculator.R
import com.example.bmicalculator.ui.settings.SettingsViewModel
import kotlin.math.pow

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    var height: Double = 0.0
    var weight: Double = 0.0
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
        val BMIout: TextView = root.findViewById(R.id.textView1)
        val settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        //TODO secure from too big vals
        weightInput.addTextChangedListener {
            Log.i("BEN",it.toString())
            Log.i("BEN",settingsViewModel.getImperial().toString())
            weight = it.toString().toDouble()
            setBMI(BMIout)
        }
        heightInput.addTextChangedListener {
            Log.i("BEN",it.toString())
            Log.i("BEN",settingsViewModel.getImperial().toString())
            height = it.toString().toDouble()
            setBMI(BMIout)
        }
        /*val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }

    fun setBMI(view: TextView){
        //todo check setting
        if(weight > 0 && height > 0) {
            view.text = getBMIMetric(weight,height).toString()
        }
    }

    fun getBMIMetric(weight: Double, height: Double): Double{
        if( height > 0){
            return weight/ height.pow(2)
        }
        return 0.0
    }

    fun getBMIImperial(weight: Double, height: Double): Double{
        if( height > 0) {
            return weight / height.pow(2) * 703
        }
        return 0.0
    }

}

package com.example.bmicalculator.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.bmicalculator.R
import kotlinx.android.synthetic.main.activity_main.*

class SettingsFragment : Fragment() {

    //private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var imperialUnits: Switch

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        var settingsViewModel =
                ViewModelProviders.of(this.requireActivity()).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        imperialUnits = root.findViewById(R.id.switch1)
        Log.i("BENI"," " + settingsViewModel.getImperial())
        imperialUnits.isChecked = settingsViewModel.getImperial()
        imperialUnits.setOnClickListener {
            settingsViewModel.toggleImperial()
            Log.i("BENI"," " + settingsViewModel.getImperial())
        }
        return root
    }



}

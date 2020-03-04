package com.example.bmicalculator.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class SettingsViewModel(private val state: SavedStateHandle) : ViewModel() {

    fun toggleImperial(){
        state.set("imperialUnits", !getImperial())
    }

    fun getImperial(): Boolean{
        return state.get<Boolean>("imperialUnits") ?: false
    }

}
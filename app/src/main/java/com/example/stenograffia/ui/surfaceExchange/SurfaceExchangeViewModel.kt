package com.example.stenograffia.ui.surfaceExchange

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SurfaceExchangeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is surface exchange Fragment"
    }
    val text: LiveData<String> = _text

}
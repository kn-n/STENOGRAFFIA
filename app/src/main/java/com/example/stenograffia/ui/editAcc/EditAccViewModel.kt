package com.example.stenograffia.ui.editAcc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditAccViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is edit Fragment"
    }
    val text: LiveData<String> = _text
}
package com.example.stenograffia.ui.surfaceExchange

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stenograffia.ui.data.firebase.NODE_SURFACE_EXCHANGE
import com.example.stenograffia.ui.data.firebase.REF_STORAGE_ROOT
import com.example.stenograffia.ui.data.firebase.initFirebase

class SurfaceExchangeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        initFirebase()
        REF_STORAGE_ROOT.child(NODE_SURFACE_EXCHANGE).downloadUrl.addOnCompleteListener {

        }
        value = "This is surface exchange Fragment"
    }
    val text: LiveData<String> = _text

}
package com.example.stenograffia.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stenograffia.ui.data.Models.SimplePlace
import com.example.stenograffia.ui.data.firebase.AppValueEventListener
import com.example.stenograffia.ui.data.firebase.NODE_ALL_PLACES
import com.example.stenograffia.ui.data.firebase.REF_DATABASE_ROOT
import com.example.stenograffia.ui.data.firebase.initFirebase

class MapViewModel : ViewModel() {

    private val _places = MutableLiveData<ArrayList<SimplePlace?>>().apply {
        initFirebase()
        REF_DATABASE_ROOT.child(NODE_ALL_PLACES).addValueEventListener(
            AppValueEventListener{ res ->
                val allPlaces = res.children.map { it.getValue(SimplePlace::class.java) }
                value = ArrayList(allPlaces)
            }
        )
    }
    val places: LiveData<ArrayList<SimplePlace?>> = _places
}
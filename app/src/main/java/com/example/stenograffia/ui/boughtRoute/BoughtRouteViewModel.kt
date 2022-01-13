package com.example.stenograffia.ui.boughtRoute

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stenograffia.ui.data.Models.LatLngDouble
import com.example.stenograffia.ui.data.Models.Point
import com.example.stenograffia.ui.data.firebase.AppValueEventListener
import com.example.stenograffia.ui.data.firebase.NODE_ROUTES
import com.example.stenograffia.ui.data.firebase.REF_DATABASE_ROOT
import com.example.stenograffia.ui.data.firebase.initFirebase
import com.google.android.gms.maps.model.LatLng

class BoughtRouteViewModel(private val id: String) : ViewModel() {
    private val _points = MutableLiveData<ArrayList<Point?>>().apply {
        initFirebase()
        REF_DATABASE_ROOT.child("Places").addValueEventListener(
            AppValueEventListener{ res ->
                val point = res.children.map { it.getValue(Point::class.java) }
                value = ArrayList(point)
            }
        )
    }
    val points: LiveData<ArrayList<Point?>> = _points

    private val _placesId = MutableLiveData<ArrayList<String?>>().apply {
        initFirebase()
        REF_DATABASE_ROOT.child(NODE_ROUTES).child(id).child("Points").addValueEventListener(
            AppValueEventListener { res ->
                val points = res.children.map { it.getValue(String::class.java) }
                value = ArrayList(points)
            }
        )
    }
    val placesId: LiveData<ArrayList<String?>> = _placesId
}
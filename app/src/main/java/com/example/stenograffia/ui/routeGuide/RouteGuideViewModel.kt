package com.example.stenograffia.ui.routeGuide

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stenograffia.ui.data.Models.Place
import com.example.stenograffia.ui.data.firebase.AppValueEventListener
import com.example.stenograffia.ui.data.firebase.NODE_ROUTES
import com.example.stenograffia.ui.data.firebase.REF_DATABASE_ROOT
import com.example.stenograffia.ui.data.firebase.initFirebase

class RouteGuideViewModel (private val routeId: String, private val placeId: String): ViewModel() {
    private val _place = MutableLiveData<Place>().apply {
        initFirebase()
        REF_DATABASE_ROOT.child(NODE_ROUTES).child(routeId).child("Points").child(placeId).addValueEventListener(
            AppValueEventListener{
                val place = it.getValue(Place::class.java)
                value = place
            }
        )
    }
    val place: LiveData<Place> = _place
}
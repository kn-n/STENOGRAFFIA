package com.example.stenograffia.ui.routeGuide

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stenograffia.ui.route.RouteViewModel

class RouteGuideModelFactory(val routeId: String, val placeId: String): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == RouteGuideViewModel::class.java) {
            RouteGuideViewModel(routeId, placeId) as T
        } else null!!
    }

}
package com.example.stenograffia.ui.boughtRoute

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stenograffia.ui.route.RouteViewModel

class BoughtRouteModelFactory(val id: String): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == BoughtRouteViewModel::class.java) {
            BoughtRouteViewModel(id) as T
        } else null!!
    }

}
package com.example.stenograffia.ui.route

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stenograffia.ui.data.Models.Route
import com.example.stenograffia.ui.data.firebase.AppValueEventListener
import com.example.stenograffia.ui.data.firebase.NODE_ROUTES
import com.example.stenograffia.ui.data.firebase.REF_DATABASE_ROOT
import com.example.stenograffia.ui.data.firebase.initFirebase

class RouteViewModel(val id: String) : ViewModel() {

//    private val _allRoutes = MutableLiveData<ArrayList<Route?>>().apply {
//        initFirebase()
//        REF_DATABASE_ROOT.child(NODE_ROUTES).addListenerForSingleValueEvent(
//            AppValueEventListener { result ->
//                val routes = result.children.map { it.getValue(Route::class.java) }
//                value = ArrayList(routes)
//            }
//        )
//    }
//    val allRoutes: LiveData<ArrayList<Route?>> = _allRoutes

    private val _allRoutes = MutableLiveData<Route>().apply {
        initFirebase()
        REF_DATABASE_ROOT.child(NODE_ROUTES).child(id).addListenerForSingleValueEvent(
            AppValueEventListener { result ->
                val route = result.getValue(Route::class.java)
                value = route
            }
        )
    }
    val allRoutes: LiveData<Route> = _allRoutes
}
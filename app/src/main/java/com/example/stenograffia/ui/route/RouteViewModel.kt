package com.example.stenograffia.ui.route

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stenograffia.ui.data.Models.Route
import com.example.stenograffia.ui.data.firebase.*

class RouteViewModel(val id: String) : ViewModel() {

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

    private val _statusOfRoute = MutableLiveData<Boolean>().apply {
        initFirebase()
        REF_DATABASE_ROOT.child(NODE_USERS).child(AUTH.currentUser!!.uid).child("boughtRoutes").addValueEventListener(
            AppValueEventListener{ array ->
                val arrayIds = array.children.map { it.getValue(String ::class.java) }
                value = arrayIds.contains(id)
            }
        )
    }
    val statusOfRoute: LiveData<Boolean> = _statusOfRoute
}
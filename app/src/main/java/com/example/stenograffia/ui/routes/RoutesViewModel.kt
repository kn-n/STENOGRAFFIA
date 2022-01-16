package com.example.stenograffia.ui.routes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stenograffia.ui.data.Models.SimpleRoute
import com.example.stenograffia.ui.data.firebase.*
import com.google.firebase.database.ktx.getValue

class RoutesViewModel : ViewModel() {

    private val _allRoutes = MutableLiveData<ArrayList<SimpleRoute?>>().apply {
        initFirebase()
        REF_DATABASE_ROOT.child(NODE_ROUTES).addValueEventListener(
            AppValueEventListener { result ->
                val routes = result.children.map { it.getValue(SimpleRoute::class.java) }
                value = ArrayList(routes)
            }
        )
    }
    val allRoutes: LiveData<ArrayList<SimpleRoute?>> = _allRoutes

    private val _userRoutes = MutableLiveData<ArrayList<SimpleRoute?>>().apply {
        initFirebase()
        REF_DATABASE_ROOT.child(NODE_USERS).child(AUTH.currentUser!!.uid).child("boughtRoutes")
            .addValueEventListener(
                AppValueEventListener { array ->
                    val arrayIds = array.children.map { it.getValue(String::class.java) }
                    val routesIds = ArrayList(arrayIds)
                    if (routesIds.isNullOrEmpty()) {
                        value = ArrayList()
                    } else {
                        REF_DATABASE_ROOT.child(NODE_ROUTES).addValueEventListener(
                            AppValueEventListener { result ->
                                val allRoutes =
                                    result.children.map { it.getValue(SimpleRoute::class.java) }
                                val resultRoutes: ArrayList<SimpleRoute?> = ArrayList()
                                for (id in routesIds) {
                                    resultRoutes.add(allRoutes[id!!.toInt() - 1])
                                }
                                value = resultRoutes
                            }
                        )
                    }
                }
            )
    }
    val userRoutes: LiveData<ArrayList<SimpleRoute?>> = _userRoutes
}
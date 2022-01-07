package com.example.stenograffia.ui.routes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stenograffia.ui.data.Models.SimpleRoute
import com.example.stenograffia.ui.data.firebase.*
import com.google.firebase.database.ktx.getValue

class RoutesViewModel : ViewModel() {

    private val _simpleRoutes = MutableLiveData<ArrayList<SimpleRoute?>>().apply {
        initFirebase()
        REF_DATABASE_ROOT.child(NODE_ROUTES).addListenerForSingleValueEvent(
            AppValueEventListener{ result ->
                val routes = result.children.map { it.getValue(SimpleRoute::class.java) }
                value = ArrayList(routes)
            }
        )
    }
    val simpleRoutes: LiveData<ArrayList<SimpleRoute?>> = _simpleRoutes

    private val _userRoutes = MutableLiveData<ArrayList<SimpleRoute?>>().apply {
        initFirebase()
        REF_DATABASE_ROOT.child(NODE_USERS).child("boughtRoutes").addListenerForSingleValueEvent(
            AppValueEventListener{
//                val routesId = it.getValue(ArrayList<String>)
            }
        )
    }
    val userRoutes: LiveData<ArrayList<SimpleRoute?>> = _userRoutes
}
package com.example.stenograffia.ui.data.firebase

import android.util.Log
import android.widget.Toast
import com.example.stenograffia.ui.data.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlin.coroutines.coroutineContext

lateinit var AUTH: FirebaseAuth
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var REF_STORAGE_ROOT: StorageReference

const val NODE_USERS = "Users"
const val NODE_ROUTES = "Routes"

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance("https://stenograffia-default-rtdb.europe-west1.firebasedatabase.app/").reference
    REF_STORAGE_ROOT = FirebaseStorage.getInstance().reference
}

fun addNewUser(user: User) {
    initFirebase()
    Log.d("UP/IN/OUT", "try add new user")
    REF_DATABASE_ROOT.child(NODE_USERS).child(user.id).setValue(user)
}

fun buyRoute(user: User, routeId: String) {
    initFirebase()
    REF_DATABASE_ROOT.child(NODE_USERS).child(user.id).child(user.boughtRoutes).child(routeId).setValue(routeId)
}

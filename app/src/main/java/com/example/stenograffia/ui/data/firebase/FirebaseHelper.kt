package com.example.stenograffia.ui.data.firebase

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
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
const val FOLDER_PROFILE_IMAGE = "ProfileImage"

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance("https://stenograffia-default-rtdb.europe-west1.firebasedatabase.app/").reference
    REF_STORAGE_ROOT = FirebaseStorage.getInstance("gs://stenograffia.appspot.com").reference
}

fun addNewUser(user: User) {
    initFirebase()
    Log.d("UP/IN/OUT", "try add new user")
    REF_DATABASE_ROOT.child(NODE_USERS).child(user.id).setValue(user)
//    val uri = user.imgUri.toUri()
//    REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE).child(user.id).putFile(uri)
}

fun buyRoute(id: String, routeId: String) {
    initFirebase()
    REF_DATABASE_ROOT.child(NODE_USERS).child(id).child("boughtRoutes").addListenerForSingleValueEvent(
        AppValueEventListener{array ->
            val routesIds = array.children.map { it.getValue(String ::class.java) }
            if (routesIds.isNullOrEmpty()){
                val rIds = arrayListOf(routeId)
                REF_DATABASE_ROOT.child(NODE_USERS).child(id).child("boughtRoutes").setValue(rIds)
            } else {
                val rIds : MutableList<String> = ArrayList()
                for (id in routesIds){
                    rIds.add(id!!)
                }
                rIds.add(routeId)
                REF_DATABASE_ROOT.child(NODE_USERS).child(id).child("boughtRoutes").setValue(rIds)
            }
        }
    )
}


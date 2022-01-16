package com.example.stenograffia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stenograffia.ui.data.Models.User
import com.example.stenograffia.ui.data.firebase.*

class MenuViewModel : ViewModel() {

    private val _user = MutableLiveData<User>().apply {
        initFirebase()
        REF_DATABASE_ROOT.child(NODE_USERS).child(AUTH.currentUser!!.uid).addValueEventListener(
            AppValueEventListener {
                val userFromFirebase = it.getValue(User::class.java)
                value = userFromFirebase
            }
        )
    }
    val user: LiveData<User> = _user
}
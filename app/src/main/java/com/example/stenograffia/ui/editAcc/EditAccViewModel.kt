package com.example.stenograffia.ui.editAcc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stenograffia.ui.data.Models.User
import com.example.stenograffia.ui.data.firebase.*

class EditAccViewModel : ViewModel() {

    private val _user = MutableLiveData<User>().apply {
        initFirebase()
        REF_DATABASE_ROOT.child(NODE_USERS).child(AUTH.currentUser!!.uid).addListenerForSingleValueEvent(
            AppValueEventListener{
                val userFromFirebase = it.getValue(User::class.java)
                value = userFromFirebase
            }
        )
    }
    val user: LiveData<User> = _user
}
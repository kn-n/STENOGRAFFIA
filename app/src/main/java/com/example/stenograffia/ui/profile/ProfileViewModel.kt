package com.example.stenograffia.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stenograffia.downloadAndSetImage
import com.example.stenograffia.ui.data.Models.User
import com.example.stenograffia.ui.data.firebase.*
import kotlinx.coroutines.currentCoroutineContext

class ProfileViewModel : ViewModel() {

    private val _userName = MutableLiveData<String>().apply {
        initFirebase()
        REF_DATABASE_ROOT.child(NODE_USERS).child(AUTH.currentUser!!.uid).addListenerForSingleValueEvent(
            AppValueEventListener{
                val user = it.getValue(User::class.java)
                value = user!!.name
            }
        )
    }
    val userName: LiveData<String> = _userName

    private val _userEmail = MutableLiveData<String>().apply {
        initFirebase()
        value = AUTH.currentUser!!.email
    }
    val userEmail: LiveData<String> = _userEmail

    private val _userImg = MutableLiveData<String>().apply {
        initFirebase()
        REF_DATABASE_ROOT.child(NODE_USERS).child(AUTH.currentUser!!.uid).child("imgUri").addValueEventListener(
            AppValueEventListener{
                val imgUri = it.getValue(String::class.java)
                value = imgUri
            })
    }
    val userImg: LiveData<String> = _userImg
}
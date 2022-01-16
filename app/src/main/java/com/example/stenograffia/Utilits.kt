package com.example.stenograffia

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.stenograffia.ui.data.firebase.FOLDER_PROFILE_IMAGE
import com.example.stenograffia.ui.data.firebase.REF_STORAGE_ROOT
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

fun CircleImageView.downloadAndSetImage(url: String) {
    if (url == "") {
        val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE)
            .child("user.png")
        path.downloadUrl.addOnCompleteListener {
            val photoUrl = it.result.toString()
            Picasso.get()
                .load(photoUrl)
                .into(this)
        }
    } else {
        Picasso.get()
            .load(url)
            .into(this)
    }
}

fun ImageView.downloadAndSetImage(url: String) {
    if (url == "") {
        val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE)
            .child("user.png")
        path.downloadUrl.addOnCompleteListener {
            val photoUrl = it.result.toString()
            Picasso.get()
                .load(photoUrl)
                .into(this)
        }
    } else {
        Picasso.get()
            .load(url)
            .into(this)
    }
}

fun loading(activity:AppCompatActivity,container: ImageView){
    Glide.with(activity).load(R.drawable.gif1).into(container)
}
fun loading(fragment:Fragment,container: ImageView){
    Glide.with(fragment).load(R.drawable.gif1).into(container)
}


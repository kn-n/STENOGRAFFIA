package com.example.stenograffia

import android.widget.ImageView
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
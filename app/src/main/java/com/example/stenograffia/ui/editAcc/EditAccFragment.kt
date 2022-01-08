package com.example.stenograffia.ui.editAcc

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.stenograffia.MenuActivity
import com.example.stenograffia.R
import com.example.stenograffia.downloadAndSetImage
import com.example.stenograffia.ui.data.firebase.*
import com.github.dhaval2404.imagepicker.ImagePicker
import de.hdodenhof.circleimageview.CircleImageView

class EditAccFragment : Fragment() {

    private lateinit var editAccViewModel: EditAccViewModel
    var mProfileUri: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_edit_acc, container, false)

        //Find view by id
        val img = root.findViewById<CircleImageView>(R.id.img)
        val username = root.findViewById<EditText>(R.id.username)
        val email = root.findViewById<EditText>(R.id.email)
        val password = root.findViewById<EditText>(R.id.password)
        val btnSave = root.findViewById<Button>(R.id.save)
        val pathForImg = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE).child(AUTH.currentUser!!.uid)

        editAccViewModel =
            ViewModelProvider(this).get(EditAccViewModel::class.java)

        editAccViewModel.user.observe(viewLifecycleOwner, Observer {
            img.downloadAndSetImage(it.imgUri)
            username.setText(it.name)
            email.setText(AUTH.currentUser!!.email)
        })

        img.setOnClickListener {
            ImagePicker
                .with(this)
                .cropSquare()
                .galleryOnly()
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }

        btnSave.setOnClickListener { view ->
            if (password.text.isNotEmpty()) {
                AUTH.currentUser!!.updatePassword(password.text.toString())
            }
            if (email.text.isNotEmpty()) {
                AUTH.currentUser!!.updateEmail(email.text.toString())
            }
            REF_DATABASE_ROOT.child(NODE_USERS).child(AUTH.currentUser!!.uid).child("name")
                .setValue(username.text.toString())
            pathForImg.downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {
                    val urlFromStorage = it.result.toString()
                    REF_DATABASE_ROOT.child(NODE_USERS).child(AUTH.currentUser!!.uid)
                        .child("imgUri").setValue(urlFromStorage)
                    view.findNavController().navigate(R.id.nav_profile)
                }
            }
        }

        return root
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!
                val img = requireView().findViewById<CircleImageView>(R.id.img)
                mProfileUri = fileUri.toString()
                REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE).child(AUTH.currentUser!!.uid).putFile(mProfileUri.toUri())
                img.setImageURI(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
        }
}
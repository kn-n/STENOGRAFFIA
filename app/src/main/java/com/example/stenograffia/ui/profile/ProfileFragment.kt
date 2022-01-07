package com.example.stenograffia.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.stenograffia.R
import com.example.stenograffia.downloadAndSetImage
import com.example.stenograffia.ui.data.Models.User
import com.example.stenograffia.ui.data.firebase.*
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        initFirebase()

        //Find view by id
        val imgProfile = root.findViewById<CircleImageView>(R.id.user_img)
        val userName = root.findViewById<TextView>(R.id.user_name)
        val userEmail = root.findViewById<TextView>(R.id.user_email)
        val btnEdit = root.findViewById<Button>(R.id.btn_edit)

        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        profileViewModel.text.observe(viewLifecycleOwner, Observer {
        })

        REF_DATABASE_ROOT.child(NODE_USERS).child(AUTH.currentUser!!.uid).addListenerForSingleValueEvent(
            AppValueEventListener{
                val user = it.getValue(User::class.java)
                userName.text = user!!.name
                userEmail.text = AUTH.currentUser!!.email
                imgProfile.downloadAndSetImage(user.imgUri)
            }
        )

        btnEdit.setOnClickListener { view ->
            view.findNavController().navigate(R.id.editAccFragment)
        }

        return root
    }
}
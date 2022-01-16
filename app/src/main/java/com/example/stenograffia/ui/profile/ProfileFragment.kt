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
        //Find view by id

        val imgProfile = root.findViewById<CircleImageView>(R.id.user_img)
        val userName = root.findViewById<TextView>(R.id.user_name)
        val userEmail = root.findViewById<TextView>(R.id.user_email)
        val btnEdit = root.findViewById<Button>(R.id.btn_edit)

        initFirebase()
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        profileViewModel.userName.observe(viewLifecycleOwner, Observer {
            userName.text = it
        })

        profileViewModel.userEmail.observe(viewLifecycleOwner, Observer {
            userEmail.text = it
        })

        profileViewModel.userImg.observe(viewLifecycleOwner, Observer {
            imgProfile.downloadAndSetImage(it)
        })

        btnEdit.setOnClickListener { view ->
            view.findNavController().navigate(R.id.editAccFragment)
        }

        return root
    }
}
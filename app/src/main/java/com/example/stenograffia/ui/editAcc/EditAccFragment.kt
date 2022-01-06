package com.example.stenograffia.ui.editAcc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.stenograffia.R
import de.hdodenhof.circleimageview.CircleImageView

class EditAccFragment : Fragment() {

    private lateinit var editAccViewModel: EditAccViewModel

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

        editAccViewModel =
            ViewModelProvider(this).get(EditAccViewModel::class.java)

        editAccViewModel.text.observe(viewLifecycleOwner, Observer {

        })

        btnSave.setOnClickListener { view ->
            view.findNavController().navigate(R.id.nav_profile)
        }

        return root
    }

}
package com.example.stenograffia.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.stenograffia.MenuActivity
import com.example.stenograffia.R
import com.example.stenograffia.ui.surfaceExchange.SurfaceExchangeViewModel

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        //Find view by id
        val paymentMethod = root.findViewById<LinearLayout>(R.id.payment_method)
        val confidential = root.findViewById<LinearLayout>(R.id.confidential)
        val aboutUs = root.findViewById<LinearLayout>(R.id.about_us)
        val help = root.findViewById<LinearLayout>(R.id.help)
        val logOut = root.findViewById<LinearLayout>(R.id.log_out)

        settingsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)

        settingsViewModel.text.observe(viewLifecycleOwner, Observer {
        })

        logOut.setOnClickListener {
        }

        return root
    }
}
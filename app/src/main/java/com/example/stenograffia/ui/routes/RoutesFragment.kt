package com.example.stenograffia.ui.routes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.stenograffia.R

class RoutesFragment : Fragment() {

    private lateinit var routesViewModel: RoutesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        routesViewModel =
            ViewModelProvider(this).get(RoutesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_routes, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        routesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
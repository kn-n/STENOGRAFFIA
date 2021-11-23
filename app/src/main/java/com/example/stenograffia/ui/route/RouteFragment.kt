package com.example.stenograffia.ui.route

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.stenograffia.R

class RouteFragment : Fragment() {

    private lateinit var routeViewModel: RouteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_route, container, false)

        //Find view by id


        routeViewModel =
            ViewModelProvider(this).get(RouteViewModel::class.java)

        routeViewModel.text.observe(viewLifecycleOwner, Observer {

        })

        return root
    }
}
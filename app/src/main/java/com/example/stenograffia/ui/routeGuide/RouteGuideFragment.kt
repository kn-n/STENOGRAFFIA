package com.example.stenograffia.ui.routeGuide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.stenograffia.R

class RouteGuideFragment:Fragment() {

    private lateinit var routeGuideViewModel: RouteGuideViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_route_guide, container, false)

        routeGuideViewModel = ViewModelProvider(this).get(RouteGuideViewModel::class.java)



        return root
    }
}
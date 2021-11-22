package com.example.stenograffia.ui.routes

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
import androidx.recyclerview.widget.RecyclerView
import com.example.stenograffia.R

class RoutesFragment : Fragment() {

    private lateinit var routesViewModel: RoutesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_routes, container, false)

        //Find view by id
//        val routeOne: Button = root.findViewById(R.id.route_one)
        val allRoutes: TextView = root.findViewById(R.id.all_routes)
        val myRoutes: TextView = root.findViewById(R.id.my_routes)
        val routesRecyclerView: RecyclerView = root.findViewById(R.id.routes_recycler_view)

        routesViewModel =
            ViewModelProvider(this).get(RoutesViewModel::class.java)

        routesViewModel.text.observe(viewLifecycleOwner, Observer {
        })

//        routeOne.setOnClickListener { view ->
//            view.findNavController().navigate(R.id.routeFragment)
//        }
        return root
    }
}
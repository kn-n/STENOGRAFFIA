package com.example.stenograffia.ui.routes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stenograffia.R
import com.example.stenograffia.ui.data.Models.SimpleRoute
import com.example.stenograffia.ui.data.firebase.AUTH
import com.example.stenograffia.ui.data.firebase.NODE_USERS
import com.example.stenograffia.ui.data.firebase.REF_DATABASE_ROOT
import com.example.stenograffia.ui.data.firebase.initFirebase

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
        routesRecyclerView.layoutManager = LinearLayoutManager(context)

        selectAllRoutes(allRoutes, myRoutes)

        var routes: ArrayList<SimpleRoute?>

        routesViewModel =
            ViewModelProvider(this).get(RoutesViewModel::class.java)

        routesViewModel.allRoutes.observe(viewLifecycleOwner, Observer {
            routes = it
            routesRecyclerView.adapter = RoutesAdapter(routes)
        })

        myRoutes.setOnClickListener {
            selectMyRoutes(allRoutes, myRoutes)

            routesViewModel.userRoutes.observe(viewLifecycleOwner, Observer {
                routes = it
                routesRecyclerView.adapter = RoutesAdapter(routes)
            })
        }

        allRoutes.setOnClickListener {
            selectAllRoutes(allRoutes, myRoutes)

            routesViewModel.allRoutes.observe(viewLifecycleOwner, Observer {
                routes = it
                routesRecyclerView.adapter = RoutesAdapter(routes)
            })
        }

//        routeOne.setOnClickListener { view ->
//            view.findNavController().navigate(R.id.routeFragment)
//        }
        return root
    }

    fun selectAllRoutes(allRoutes: TextView, myRoutes: TextView){
        allRoutes.setTextAppearance(R.style.selected_file_routes)
        allRoutes.setBackgroundResource(R.drawable.underlined)
        myRoutes.setTextAppearance(R.style.unselected_file_routes)
        myRoutes.setBackgroundResource(R.drawable.not_underlined)
    }

    fun selectMyRoutes(allRoutes: TextView, myRoutes: TextView){
        myRoutes.setTextAppearance(R.style.selected_file_routes)
        myRoutes.setBackgroundResource(R.drawable.underlined)
        allRoutes.setTextAppearance(R.style.unselected_file_routes)
        allRoutes.setBackgroundResource(R.drawable.not_underlined)
    }
}
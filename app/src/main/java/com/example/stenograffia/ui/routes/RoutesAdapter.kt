package com.example.stenograffia.ui.routes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.stenograffia.R
import com.example.stenograffia.ui.data.Models.SimpleRoute

class RoutesAdapter(private val routes: ArrayList<SimpleRoute?>, private val fromAllRoutes: Boolean):RecyclerView.Adapter<RoutesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_routes_recycler_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val route = routes[position]
        holder.title.text = route!!.Name
        holder.popularPlaces.text = route.PopularPlaces
        holder.routeLength.text = route.Length
        val bundle = Bundle()
        bundle.putString("routeId", route.Id)
        if (fromAllRoutes){
            holder.route.setOnClickListener { view ->
                view.findNavController().navigate(R.id.routeFragment, bundle)
            }
        }else{
            holder.route.setOnClickListener { view ->
                view.findNavController().navigate(R.id.boughtRouteFragment, bundle)
            }
        }

    }

    override fun getItemCount() = routes.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val popularPlaces: TextView = itemView.findViewById(R.id.popular_objects)
        val routeLength: TextView = itemView.findViewById(R.id.route_length)
        val route: LinearLayout = itemView.findViewById(R.id.frame)
    }
}
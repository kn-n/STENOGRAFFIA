package com.example.stenograffia.ui.route

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.stenograffia.R
import com.example.stenograffia.ui.data.firebase.AUTH
import com.example.stenograffia.ui.data.firebase.buyRoute
import com.example.stenograffia.ui.data.firebase.initFirebase

class RouteFragment : Fragment() {

    private lateinit var routeViewModel: RouteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_route, container, false)

        val routeId = requireArguments().getString("routeId")

        //Find view by id
        val description: TextView = root.findViewById(R.id.inf_object)
        val price: TextView = root.findViewById(R.id.price)
        val btnBuy : Button = root.findViewById(R.id.btn_buy)

        routeViewModel =
            ViewModelProvider(this, ModelFactory(routeId!!)).get(RouteViewModel::class.java)

        routeViewModel.allRoutes.observe(viewLifecycleOwner, Observer { route ->
            description.text = route.Description
            price.text = route.Price
            btnBuy.setOnClickListener {
                initFirebase()
                buyRoute(AUTH.currentUser!!.uid, route.Id)
                btnBuy.visibility = View.GONE
            }
        })

        routeViewModel.statusOfRoute.observe(viewLifecycleOwner, Observer {
            if (it) btnBuy.visibility = View.GONE
            else btnBuy.visibility = View.VISIBLE
        })

        return root
    }
}
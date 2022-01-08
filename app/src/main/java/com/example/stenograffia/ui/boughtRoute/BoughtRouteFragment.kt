package com.example.stenograffia.ui.boughtRoute

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
import com.example.stenograffia.ui.data.Models.Point
import com.example.stenograffia.ui.route.ModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class BoughtRouteFragment:Fragment(), GoogleMap.OnMarkerClickListener {

    private lateinit var boughtRouteViewModel: BoughtRouteViewModel
    lateinit var map: SupportMapFragment
    lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_route_map, container, false)

        val routeId = requireArguments().getString("routeId")

        //Find view by id
        map = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        boughtRouteViewModel = ViewModelProvider(this,  ModelFactory(routeId!!)).get(BoughtRouteViewModel::class.java)

//        boughtRouteViewModel.text.observe(viewLifecycleOwner, Observer {
//
//        })

        map.onCreate(savedInstanceState)
        map.getMapAsync(OnMapReadyCallback {
            googleMap = it
            val ekbBounds = LatLngBounds(
                LatLng((56.821496), 	60.578646),// SW bounds
                LatLng((56.892695), 60.652224)// NE bounds
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ekbBounds.center, 11.5f))
            googleMap.setOnMarkerClickListener(this)
            val flowerPortal = LatLng(56.829890, 60.600504)
            googleMap.addMarker(
                MarkerOptions()
                    .position(flowerPortal)
                    .title("Цветочный портал")
            )
        })



        return root
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        boughtRouteViewModel.placesId.observe(viewLifecycleOwner, Observer {
            val points = it
            val markerPoint = Point(marker.position.latitude.toString(), marker.position.longitude.toString())
            val i = 0
            while (i<points.size){
                if (points[i] == markerPoint){
                    val bundle = Bundle()
                    bundle.getString("placeId", (i+1).toString())
                    view?.findNavController()?.navigate(R.id.routeGuideFragment)
                }
            }

        })
        return false
    }
}
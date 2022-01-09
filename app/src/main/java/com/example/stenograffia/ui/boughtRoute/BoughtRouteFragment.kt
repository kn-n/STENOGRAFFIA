package com.example.stenograffia.ui.boughtRoute

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.stenograffia.R
import com.example.stenograffia.ui.data.Models.Point
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
    private var idRoute: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_route_map, container, false)

        val routeId = requireArguments().getString("routeId")

        idRoute = routeId!!
        //Find view by id
        map = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        boughtRouteViewModel = ViewModelProvider(this,  BoughtRouteModelFactory(routeId)).get(BoughtRouteViewModel::class.java)

        map.onCreate(savedInstanceState)
        map.getMapAsync(OnMapReadyCallback {
            googleMap = it
            val ekbBounds = LatLngBounds(
                LatLng((56.821496), 	60.578646),// SW bounds
                LatLng((56.892695), 60.652224)// NE bounds
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ekbBounds.center, 11.5f))
            googleMap.setOnMarkerClickListener(this)

            boughtRouteViewModel.placesId.observe(viewLifecycleOwner, Observer { points ->
                for (point in points){
                    val marker = LatLng(point!!.latitude.toDouble(), point.longitude.toDouble())
                    googleMap.addMarker(
                        MarkerOptions()
                            .position(marker)
                    )
                }
            })
//            val flowerPortal = LatLng(56.829890, 60.600504)
//            googleMap.addMarker(
//                MarkerOptions()
//                    .position(flowerPortal)
//                    .title("Цветочный портал")
//            )
        })



        return root
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        boughtRouteViewModel.placesId.observe(viewLifecycleOwner, Observer {
            val bundle = Bundle()
            val points = it
            val markerPoint = Point(marker.position.latitude.toString(), marker.position.longitude.toString())
            var i = 0
            while (i<points.size){
                if (points[i]!!.latitude == markerPoint.latitude && points[i]!!.longitude == markerPoint.longitude){
                    bundle.putString("routeId", idRoute)
                    bundle.putString("placeId", (i+1).toString())
                    break
                } else {
                    i+=1
                }
            }
            view?.findNavController()?.navigate(R.id.routeGuideFragment, bundle)
        })
        return false
    }
}
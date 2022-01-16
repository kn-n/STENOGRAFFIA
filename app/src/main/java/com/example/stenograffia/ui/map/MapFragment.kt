package com.example.stenograffia.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.stenograffia.R
import com.example.stenograffia.ui.data.firebase.NODE_ROUTES
import com.example.stenograffia.ui.data.firebase.REF_DATABASE_ROOT
import com.example.stenograffia.ui.data.firebase.initFirebase
import com.google.android.gms.maps.*
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(){

    private lateinit var mapViewModel: MapViewModel
    lateinit var map: SupportMapFragment
    lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_map, container, false)
        //Find by id
        map = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapViewModel =
            ViewModelProvider(this).get(MapViewModel::class.java)

        map.onCreate(savedInstanceState)
        map.getMapAsync(OnMapReadyCallback {
            googleMap = it
            val ekbBounds = LatLngBounds(
                LatLng((56.821496), 	60.578646),// SW bounds
                LatLng((56.892695), 60.652224)// NE bounds
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ekbBounds.center, 11.5f))

            mapViewModel.places.observe(viewLifecycleOwner, Observer { res ->
                for (place in res){
                    val marker = LatLng(place!!.latitude.toDouble(), place.longitude.toDouble())
                    googleMap.addMarker(
                        MarkerOptions()
                            .position(marker)
                            .title(place.name)
                    )
                }
            })
        })

        return root
    }
}
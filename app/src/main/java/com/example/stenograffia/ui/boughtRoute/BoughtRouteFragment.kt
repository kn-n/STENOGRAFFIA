package com.example.stenograffia.ui.boughtRoute

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.stenograffia.R
import com.example.stenograffia.ui.data.Models.Point
import com.example.stenograffia.ui.data.firebase.NODE_ROUTES
import com.example.stenograffia.ui.data.firebase.REF_DATABASE_ROOT
import com.example.stenograffia.ui.data.firebase.initFirebase
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.android.gms.maps.model.LatLng


class BoughtRouteFragment : Fragment(), GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private lateinit var boughtRouteViewModel: BoughtRouteViewModel
    lateinit var map: SupportMapFragment
    lateinit var googleMapLate: GoogleMap
    private var idRoute: String = ""

    private var originLatitude: Double = 56.901685
    private var originLongitude: Double = 60.638509
    private var destinationLatitude: Double = 56.788791
    private var destinationLongitude: Double = 60.647781

    private var origin: LatLng = LatLng(56.838358, 60.603405)
    private var destination: LatLng = LatLng(0.0, 0.0)
    private var waypoints: String = ""
    private var apiKey: String = ""
    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            if (locationResult == null) {
                return
            }

            for (location in locationResult.locations) {
//                Log.d("hey!", "onLocationResult: $location")
//                val markerCords = LatLng(location.latitude, location.longitude)
//                googleMapLate.addMarker(
//                    MarkerOptions()
//                        .position(markerCords)
//                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_route_map, container, false)
        val routeId = requireArguments().getString("routeId")
        idRoute = routeId!!
        map = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        boughtRouteViewModel = ViewModelProvider(this, BoughtRouteModelFactory(routeId)).get(
            BoughtRouteViewModel::class.java
        )

        val ai: ApplicationInfo = requireContext().packageManager
            .getApplicationInfo(requireContext().packageName, PackageManager.GET_META_DATA)
        val value = ai.metaData["com.google.android.geo.API_KEY"]
        apiKey = value.toString()

        map.onCreate(savedInstanceState)
        map.getMapAsync(this)

        map.getMapAsync {
            googleMapLate = it
            val ekbBounds = LatLngBounds(
                LatLng((56.821496), 60.578646),// SW bounds
                LatLng((56.892695), 60.652224)// NE bounds
            )
            googleMapLate.moveCamera(CameraUpdateFactory.newLatLngZoom(ekbBounds.center, 14f))
            googleMapLate.setOnMarkerClickListener(this)
            googleMapLate.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 14F))
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        locationRequest = LocationRequest.create()
        locationRequest.interval = 4000
        locationRequest.fastestInterval = 2000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        return root
    }

    private fun getDirectionURL(
        origin: LatLng,
        dest: LatLng,
        waypoints: String,
        secret: String
    ): String {
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${dest.latitude},${dest.longitude}&waypoints=optimize:true$waypoints&mode=walking&key=$secret"
    }

    @SuppressLint("StaticFieldLeak")
    private inner class GetDirection(val url: String) :
        AsyncTask<Void, Void, List<List<LatLng>>>() {
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body()!!.string()

            val result = ArrayList<List<LatLng>>()
            try {
                val respObj = Gson().fromJson(data, MapData::class.java)
                val path = ArrayList<LatLng>()
                for (n in 0 until respObj.routes[0].legs.size) {
                    for (i in 0 until respObj.routes[0].legs[n].steps.size) {
                        path.addAll(decodePolyline(respObj.routes[0].legs[n].steps[i].polyline.points))
                    }
                }

                result.add(path)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineOptions = PolylineOptions()
            initFirebase()
            for (i in result.indices) {
                REF_DATABASE_ROOT.child(NODE_ROUTES).child(idRoute).child("polyline")
                    .setValue(result[i])
                lineOptions.addAll(result[i])
                lineOptions.width(10f)
                lineOptions.color(Color.rgb(252,214,0))
                lineOptions.geodesic(true)
            }
            googleMapLate.addPolyline(lineOptions)
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMapLate = p0
        if (ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation()
            zoomToUserLocation()
        } else {
            boughtRouteViewModel.placesId.observe(viewLifecycleOwner, Observer { places ->
                boughtRouteViewModel.points.observe(viewLifecycleOwner, Observer { points ->
                    for (point in points){
                        if (places.contains(point!!.id)){
                            when (point.id) {
                                places[places.size-1] -> destination =
                                    LatLng(point.latitude.toDouble(), point.longitude.toDouble())
                                places[0] -> origin =
                                    LatLng(point.latitude.toDouble(), point.longitude.toDouble())
                                else -> waypoints+="|${point.latitude},${point.longitude}"
                            }

                            val marker =
                                LatLng(point.latitude.toDouble(), point.longitude.toDouble())
                            googleMapLate.addMarker(
                                MarkerOptions()
                                    .position(marker)
                            )
                        }
                    }
                    googleMapLate.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 14F))
                    val urll = getDirectionURL(origin, destination, waypoints, apiKey)
                    GetDirection(urll).execute()
                    googleMapLate.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 14F))
                })
            })
        }
    }

    private fun direction(){
        boughtRouteViewModel.placesId.observe(viewLifecycleOwner, Observer { places ->
            boughtRouteViewModel.points.observe(viewLifecycleOwner, Observer { points ->
                for (point in points){
                    if (places.contains(point!!.id)){
                        if (point.id == places[places.size-1]) destination =
                            LatLng(point.latitude.toDouble(), point.longitude.toDouble())
                        waypoints+="|${point.latitude},${point.longitude}"

                        val marker =
                            LatLng(point.latitude.toDouble(), point.longitude.toDouble())

                        googleMapLate.addMarker(
                            MarkerOptions()
                                .position(marker)
                        )
                    }
                }
            googleMapLate.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 14F))
            val urll = getDirectionURL(origin, destination, waypoints, apiKey)
            GetDirection(urll).execute()
            googleMapLate.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 14F))
            })
        })
    }

    private fun enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
        }
        googleMapLate.isMyLocationEnabled = true
    }

    private fun zoomToUserLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
        }
        val locationTask: Task<Location>? = fusedLocationProviderClient.lastLocation
        locationTask!!.addOnSuccessListener {
            origin = LatLng(it.latitude, it.longitude)
            direction()
//            googleMapLate.addMarker(MarkerOptions().position(latLng))
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        boughtRouteViewModel.points.observe(viewLifecycleOwner, Observer {
            val bundle = Bundle()
            val points = it
//            var i = 0
//            while (i < points.size) {
//                if (points[i]!!.latitude == marker.position.latitude.toString() && points[i]!!.longitude == marker.position.longitude.toString()) {
//                    bundle.putString("routeId", idRoute)
//                    bundle.putString("placeId", (i + 1).toString())
//                    break
//                } else {
//                    i += 1
//                }
//            }
            for (i in 0 until points.size){
                if (points[i]!!.latitude == marker.position.latitude.toString() && points[i]!!.longitude == marker.position.longitude.toString()) {
                    bundle.putString("routeId", idRoute)
                    bundle.putString("placeId", points[i]!!.id)
                    break
                }
            }
            view?.findNavController()?.navigate(R.id.routeGuideFragment, bundle)
        })
        return false
    }

    fun decodePolyline(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val latLng = LatLng((lat.toDouble() / 1E5), (lng.toDouble() / 1E5))
            poly.add(latLng)
        }
        return poly
    }

    private fun checkSettingsAndStartLocationUpdates() {
        val request: LocationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest).build()
        val client: SettingsClient = LocationServices.getSettingsClient(requireContext())
        val locationSettingsResponse: Task<LocationSettingsResponse> =
            client.checkLocationSettings(request)
        locationSettingsResponse.addOnSuccessListener(OnSuccessListener<LocationSettingsResponse>() {
            startLocationUpdates()
        })
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
                checkSettingsAndStartLocationUpdates()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
            }
            else -> {
                // No location access granted.
            }
        }
    }

}
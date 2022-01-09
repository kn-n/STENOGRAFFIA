package com.example.stenograffia.ui.boughtRoute

import android.annotation.SuppressLint
import android.content.ContextWrapper
import android.graphics.Color
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.canhub.cropper.options
import com.example.stenograffia.R
import com.example.stenograffia.retrofit.ApiInterface
import com.example.stenograffia.retrofit.Result
import com.example.stenograffia.retrofit.RouteForRetrofit
import com.example.stenograffia.ui.data.Models.Point
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.operators.single.SingleObserveOn
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class BoughtRouteFragment : Fragment(), GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private lateinit var boughtRouteViewModel: BoughtRouteViewModel
    lateinit var map: SupportMapFragment
    lateinit var googleMapLate: GoogleMap
    private var idRoute: String = ""
    lateinit var apiInterface: ApiInterface
    private lateinit var polyLineList: List<LatLng>
    private lateinit var polylineOptions: PolylineOptions
    private lateinit var origion: LatLng
    private lateinit var dest: LatLng

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

        boughtRouteViewModel = ViewModelProvider(this, BoughtRouteModelFactory(routeId)).get(
            BoughtRouteViewModel::class.java
        )

        map.onCreate(savedInstanceState)
        map.getMapAsync(this)

//        map.getMapAsync(OnMapReadyCallback {
//
//            googleMapLate = it
//            val ekbBounds = LatLngBounds(
//                LatLng((56.848344), 60.565490),// SW bounds
//                LatLng((56.867354), 60.602902)// NE bounds
//            )
//            googleMapLate.moveCamera(CameraUpdateFactory.newLatLngZoom(ekbBounds.center, 14f))
//            googleMapLate.setOnMarkerClickListener(this)
//
//            boughtRouteViewModel.placesId.observe(viewLifecycleOwner, Observer { points ->
//                for (point in points) {
//                    val marker = LatLng(point!!.latitude.toDouble(), point.longitude.toDouble())
//                    googleMapLate.addMarker(
//                        MarkerOptions()
//                            .position(marker)
//                    )
//                }
//            })
//        })

        val retrofit: Retrofit =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://maps.googleapis.com/")
                .build()

        apiInterface = retrofit.create(ApiInterface::class.java)
        return root
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        boughtRouteViewModel.placesId.observe(viewLifecycleOwner, Observer {
            val bundle = Bundle()
            val points = it
            val markerPoint = Point(
                marker.position.latitude.toString(),
                marker.position.longitude.toString()
            )
            var i = 0
            while (i < points.size) {
                if (points[i]!!.latitude == markerPoint.latitude && points[i]!!.longitude == markerPoint.longitude) {
                    bundle.putString("routeId", idRoute)
                    bundle.putString("placeId", (i + 1).toString())
                    break
                } else {
                    i += 1
                }
            }
            view?.findNavController()?.navigate(R.id.routeGuideFragment, bundle)
        })
        return false
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMapLate = googleMap
        googleMapLate.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMapLate.isTrafficEnabled = false
        origion = LatLng(56.901685,60.638509)
        dest = LatLng(56.788791,60.647781)

        googleMapLate.addMarker(MarkerOptions().position(origion))
        googleMapLate.addMarker(MarkerOptions().position(dest))

        getDirection("56.901685" + "," + "60.638509", "56.788791" + "," + "60.647781")
    }

    @SuppressLint("CheckResult")
    private fun getDirection(origin: String, destination: String) {
        apiInterface.getDirection(
            "driving",
            "less_driving",
            origin,
            destination,
            getString(R.string.api_key)
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Result> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(t: Result) {
                    polyLineList = ArrayList()
                    val routeList: List<RouteForRetrofit> = t.getRoutes()
                    Toast.makeText(context,t.toString(),Toast.LENGTH_LONG).show()
                    for (route: RouteForRetrofit in routeList) {
                        val polyLine: String = route.getOverviewPolyline().getPoints()
                        (polyLineList as ArrayList<LatLng>).addAll(decodePolyline(polyLine))
                    }
//                    Log.d("PLEASE",polyLineList[0].toString())
                    polylineOptions = PolylineOptions()
                    polylineOptions.color(Color.BLUE)
                    polylineOptions.width(8F)
                    polylineOptions.startCap(ButtCap())
                    polylineOptions.jointType(JointType.ROUND)
                    polylineOptions.addAll(polyLineList)
                    googleMapLate.addPolyline(polylineOptions)

                    val builder: LatLngBounds.Builder = LatLngBounds.Builder()
                    builder.include(origion)
                    builder.include(dest)
                    googleMapLate.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100))
                }

                override fun onError(e: Throwable) {

                }

            })
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
            val latLng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))
            poly.add(latLng)
        }
        return poly
    }
}
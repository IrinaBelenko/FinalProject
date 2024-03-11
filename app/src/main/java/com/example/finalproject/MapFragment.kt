package com.example.finalproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil


class MapFragment : Fragment() {
    private var results: List<Results>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        supportMapFragment.getMapAsync { map ->
            val coordinates_Lviv = LatLng(49.842957, 24.031111)
            map.addMarker(MarkerOptions().position(coordinates_Lviv).title("My Position"))
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates_Lviv, 8F))

            val locations = mutableListOf<Location>()
            val placeCoordinates = mutableListOf<String>()
            results?.forEach { result ->
                val location = result.geometry.location
                locations.add(location)
                placeCoordinates.add("${result.geometry.location.lat},${result.geometry.location.lng}")
            }

            locations.forEach {
                val coordinates = LatLng(it.lat, it.lng)
                map.addMarker(MarkerOptions().position(coordinates))
            }
            val waypointCoordinates = placeCoordinates.drop(0).take(10)
            val waypointCoordinatesString =
                waypointCoordinates.joinToString(separator = "|")

            viewModel.getRoutes(
                placeCoordinates[0],
                placeCoordinates.last(),
                waypointCoordinatesString
            )

            viewModel.uiState.observe(requireActivity()) {
                when (it) {
                    is MyViewModel.UIState.Empty -> Unit
                    is MyViewModel.UIState.Result -> Unit
                    is MyViewModel.UIState.Processing -> Unit
                    is MyViewModel.UIState.ResultRoute -> {
                        val polylinePoints = it.responseResultRoute[0].overviewPolyline.points
                        val decodedPath = PolyUtil.decode(polylinePoints)
                        map.addPolyline(PolylineOptions().addAll(decodedPath))
                    }

                    is MyViewModel.UIState.Error -> {
                        Log.e("response error", it.description)
                    }
                }
            }
        }
    }

    fun setListResults(listValue: List<Results>) {
        results = listValue
    }
}
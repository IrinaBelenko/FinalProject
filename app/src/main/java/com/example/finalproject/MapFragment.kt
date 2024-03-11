package com.example.finalproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment() {
    private var results: List<Results>? = null

    //private lateinit var results: List<Results>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

            supportMapFragment.getMapAsync { map ->
                val coordinates_Lviv = LatLng(49.842957, 24.031111)
                map.addMarker(MarkerOptions().position(coordinates_Lviv).title("My Position"))
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates_Lviv, 8F))

                val locations = mutableListOf<Location>()
                val placeCoordinates = mutableListOf<String>()
                results?.forEach{result ->
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

        }
    }

    fun setListResults(listValue: List<Results>) {
        results = listValue
    }
}
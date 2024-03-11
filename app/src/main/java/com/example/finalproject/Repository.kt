package com.example.finalproject


import retrofit2.Response
import retrofit2.Retrofit

class Repository(private val client: Retrofit) {
    suspend fun getNearbyPlaces(): Response<PlacesResponse?> {
        val apiInterface = client.create(ApiInterface::class.java)
        return apiInterface.getNearbyPlaces()
    }

    suspend fun getComplexRoute(originId: String,destinationId: String,waypoints: String): Response<DirectionsResponse?> {
        val apiInterface = client.create(ApiInterface::class.java)
        return apiInterface.getComplexRoute(originId,destinationId,waypoints)
    }
}
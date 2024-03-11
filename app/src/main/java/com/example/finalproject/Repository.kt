package com.example.finalproject


import retrofit2.Response
import retrofit2.Retrofit

class Repository(private val client: Retrofit) {
    suspend fun getNearbyPlaces(): Response<PlacesResponse?> {
        val apiInterface = client.create(ApiInterface::class.java)
        return apiInterface.getNearbyPlaces()
    }
}
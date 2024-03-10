package com.example.finalproject

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/maps/api/directions/json?&origin=49.842957,24.031111&destination=49.553516,25.594767&key=AIzaSyDJber0UpLCHpq3F7zYxs5Xww1IEdgbo78")
    suspend fun getSimpleRoute(): Response<DirectionsResponse>

    @GET("/maps/api/place/nearbysearch/json?location=49.842957,24.031111&radius=2000&type=tourist_attractions&key=AIzaSyDJber0UpLCHpq3F7zYxs5Xww1IEdgbo78")
    suspend fun getNearbyPlaces():Response<PlacesResponse>

    @GET("/maps/api/directions/json?")
    suspend fun getComplexRoute(
        @Query("origin") originId: String,
        @Query("destination") destinationId: String,
        @Query("waypoints") waypoints: String,
        @Query("key") key: String = "AIzaSyDJber0UpLCHpq3F7zYxs5Xww1IEdgbo78"
    ): Response<DirectionsResponse>
}
package com.example.stenograffia.retrofit

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query
import java.util.prefs.AbstractPreferences


interface ApiInterface {
    @GET("maps/api/directions/json")
    fun getDirection(
        @Query("mode") mode: String,
        @Query("transit_routing_preferance") preferences: String,
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") key: String): Single<Result>
}
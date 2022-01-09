package com.example.stenograffia.retrofit

class Result {

    private var routes: List<RouteForRetrofit> = listOf()
    private var status: String = ""

    fun getRoutes(): List<RouteForRetrofit> { return routes }

    fun setRoutes(routes: List<RouteForRetrofit>) { this.routes = routes }

    fun getStatus():String { return status }

    fun setStatus(status: String) { this.status = status }
}
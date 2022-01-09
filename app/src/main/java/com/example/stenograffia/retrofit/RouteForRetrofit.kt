package com.example.stenograffia.retrofit

class RouteForRetrofit {

    private var overviewPolyline = OverviewPolyline()

    fun getOverviewPolyline(): OverviewPolyline { return overviewPolyline }

    fun setOverviewPolyline(overviewPolyline: OverviewPolyline) {
        this.overviewPolyline = overviewPolyline
    }
}
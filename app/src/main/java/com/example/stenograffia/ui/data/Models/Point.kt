package com.example.stenograffia.ui.data.Models

class Point {
    var latitude: String = ""
    var longitude: String = ""

    constructor()

    constructor(
        latitude: String,
        longitude: String
    ){
        this.latitude = latitude
        this.longitude = longitude
    }
}
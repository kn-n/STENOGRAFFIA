package com.example.stenograffia.ui.data.Models

class Point {
    var id: String = ""
    var latitude: String = ""
    var longitude: String = ""

    constructor()

    constructor(
        id: String,
        latitude: String,
        longitude: String
    ){
        this.id = id
        this.latitude = latitude
        this.longitude = longitude
    }
}
package com.example.stenograffia.ui.data.Models

class SimplePlace {
    var name: String = ""
    var latitude: String = ""
    var longitude: String = ""

    constructor()

    constructor(
        name: String,
        latitude: String,
        longitude: String
    ){
        this.name = name
        this.latitude = latitude
        this.longitude = longitude
    }
}
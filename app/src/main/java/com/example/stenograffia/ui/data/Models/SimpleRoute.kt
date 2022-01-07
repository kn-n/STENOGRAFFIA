package com.example.stenograffia.ui.data.Models

class SimpleRoute {
    var Name: String = ""
    var Length: String = ""
    var PopularPlaces: String = ""

    constructor()

    constructor(
        Name: String,
        Length: String,
        PopularPlaces: String
    ) {
        this.Name = Name
        this.Length = Length
        this.PopularPlaces = PopularPlaces
    }
}
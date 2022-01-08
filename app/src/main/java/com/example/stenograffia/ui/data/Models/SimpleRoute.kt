package com.example.stenograffia.ui.data.Models

class SimpleRoute {
    var Id: String = ""
    var Name: String = ""
    var Length: String = ""
    var PopularPlaces: String = ""

    constructor()

    constructor(
        Id: String,
        Name: String,
        Length: String,
        PopularPlaces: String
    ) {
        this.Id = Id
        this.Name = Name
        this.Length = Length
        this.PopularPlaces = PopularPlaces
    }
}
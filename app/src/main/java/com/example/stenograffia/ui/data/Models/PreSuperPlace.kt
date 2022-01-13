package com.example.stenograffia.ui.data.Models

class PreSuperPlace {
    var Name: String = ""
    var Description: String = ""
    var Img_url: String = ""
    var Audio: String = ""
    var latitude: String = ""
    var longitude: String = ""

    constructor()

    constructor(
        Name: String,
        Description: String,
        Img_url: String,
        Audio: String,
        latitude: String,
        longitude: String
    ){
        this.Name = Name
        this.Description = Description
        this.Img_url = Img_url
        this.Audio = Audio
        this.latitude = latitude
        this.longitude = longitude
    }
}
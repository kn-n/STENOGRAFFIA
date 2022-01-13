package com.example.stenograffia.ui.data.Models

class SuperPlace{
    var id: String = ""
    var name: String = ""
    var description: String = ""
    var img_url: String = ""
    var audio: String = ""
    var latitude: String = ""
    var longitude: String = ""

    constructor()

    constructor(
        id: String,
        name: String,
        description: String,
        img_url: String,
        audio: String,
        latitude: String,
        longitude: String
    ){
        this.id = id
        this.name = name
        this.description = description
        this.img_url = img_url
        this.audio = audio
        this.latitude = latitude
        this.longitude = longitude
    }
}
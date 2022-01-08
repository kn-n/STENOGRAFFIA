package com.example.stenograffia.ui.data.Models

class Place {
    var Name: String = ""
    var Description: String = ""
    var Img_url: String = ""
    var Audio: String = ""

    constructor()

    constructor(
        Name: String,
        Description: String,
        Img_url: String,
        Audio: String
    ){
        this.Name = Name
        this.Description = Description
        this.Img_url = Img_url
        this.Audio = Audio
    }
}
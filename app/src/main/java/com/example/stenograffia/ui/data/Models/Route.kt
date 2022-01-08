package com.example.stenograffia.ui.data.Models

import android.net.Uri

class Route {
    var Id: String = ""
    var Name: String = ""
    var Length: String = ""
    var PopularPlaces: String = ""
    var Description: String = ""
    var Price: String = ""
//    var PromoImgs: ArrayList<String> = ArrayList()
//    val Points: ArrayList<Point> = ArrayList()

    constructor()

    constructor(
        Id: String,
        Name: String,
        Length: String,
        PopularPlaces: String,
        Description: String,
        Price: String,
//        PromoImgs: ArrayList<String>
    ) {
        this.Id = Id
        this.Name = Name
        this.Length = Length
        this.PopularPlaces = PopularPlaces
        this.Description = Description
        this.Price = Price
//        this.PromoImgs = PromoImgs
    }
}
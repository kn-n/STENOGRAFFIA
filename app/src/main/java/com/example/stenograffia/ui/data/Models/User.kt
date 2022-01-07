package com.example.stenograffia.ui.data.Models

class User {

    var id: String = ""
    var name: String = ""
    var imgUri: String = ""
    var boughtRoutes: ArrayList<String> = ArrayList()

    constructor()

    constructor(
        id: String,
        name: String,
        imgUri: String,
        boughtRoutes: ArrayList<String>
    ) {
        this.id = id
        this.name = name
        this.imgUri = imgUri
        this.boughtRoutes = boughtRoutes
    }
}
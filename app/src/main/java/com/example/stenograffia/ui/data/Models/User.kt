package com.example.stenograffia.ui.data.Models

class User {

    var id: String = ""
    var name: String = ""
    var imgUrl: String = ""
    var boughtRoutes: String = ""

    constructor()

    constructor(
        id: String,
        name: String,
        imgUrl: String,
        boughtRoutes: String
    ) {
        this.id = id
        this.name = name
        this.imgUrl = imgUrl
        this.boughtRoutes = boughtRoutes
    }
}
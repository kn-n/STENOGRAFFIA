package com.example.stenograffia.ui.data.Models

class Surface {
    var sentUserId: String = ""
    var imgUrlForExchange: String = ""
    var address: String = ""
    var description: String = ""

    constructor()

    constructor(
        sentUserId: String,
        imgUrlForExchange: String,
        address: String,
        description: String
    ) {
        this.sentUserId = sentUserId
        this.imgUrlForExchange = imgUrlForExchange
        this.address = address
        this.description = description
    }

    constructor(
        sentUserId: String,
        address: String,
        description: String
    ) {
        this.sentUserId = sentUserId
        this.address = address
        this.description = description
    }
}
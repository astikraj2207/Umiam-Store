package com.example.onlinestorekotlin

class Product {
    var id : Int
    var name: String
    var price: Int
    var pictureName : String

    constructor(id:Int, name:String, price:Int, pictureName:String) {
        this.id = id
        this.name = name
        this.price = price
        this.pictureName = pictureName
    }
}
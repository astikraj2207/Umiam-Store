package com.example.onlinestorekotlin

class TemporaryProduct {
    var id : Int
    var name: String
    var price: Int
    var amount: Int
    var pictureName : String

    constructor(id:Int, name:String, price:Int, amount:Int, pictureName:String) {
        this.id = id
        this.name = name
        this.price = price
        this.amount = amount
        this.pictureName = pictureName
    }
}
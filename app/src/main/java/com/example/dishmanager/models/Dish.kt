package com.example.dishmanager.models

data class Dish(

    val dishId: Int,
    val dishName: String,
    val category: String,
    val imagePath: String,
    val finalPriceForClients: Double,
    val description: String,
    var like: Boolean = false

)
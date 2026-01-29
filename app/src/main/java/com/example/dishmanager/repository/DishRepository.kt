package com.example.dishmanager.repository

import android.content.Context
import android.util.Log
import com.example.dishmanager.models.Dish
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class DishRepository(val context: Context) {

    private val fileName = "dishes.json"
    private val jsonArray = AssetRepository(context).getJsonFromAssets(fileName)


    fun getDishes(): List<Dish> {

        Log.d("dish", jsonArray.toString())

        val dishes = mutableListOf<Dish>()

        for (position in 0 until jsonArray.length()) {

            val jsonObject = jsonArray.getJSONObject(position)

            val dish = Dish(
                dishId = jsonObject.getInt("dishId"),
                dishName = jsonObject.getString("dishName"),
                category = jsonObject.getString("category"),
                description = jsonObject.getString("description"),
                imagePath = jsonObject.getString("imagePath"),
                ingredients = jsonObject.getString("ingredients"),
                finalPriceForClients = jsonObject.getDouble("finalPriceForClients")
            )

            dishes.add(dish)

        }

        return dishes

    }

}
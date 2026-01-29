package com.example.dishmanager.repository

import android.content.Context
import com.example.dishmanager.models.Category
import com.example.dishmanager.models.Dish
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class CategoryRepository(val context: Context) {

    private val fileName = "categories.json"
    private val jsonArray = AssetRepository(context).getJsonFromAssets(fileName)


    fun getCategories(): List<String> {

        val categories = mutableListOf<String>()

        for (position in 0 until jsonArray.length()) {

            val jsonObject = jsonArray.getJSONObject(position)
            val category = jsonObject.getString("categoryName")
            categories.add(category)

        }

        return categories

    }

}
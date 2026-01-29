package com.example.dishmanager.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.dishmanager.models.Dish
import org.json.JSONArray
import org.json.JSONObject

class AssetRepository(val context: Context) {

    private val assetManager = context.assets

    fun getJsonFromAssets(fileName: String): JSONArray {

        return try {

            JSONArray(assetManager.open(fileName).use { inputStream ->
                inputStream.bufferedReader().use {
                    it.readText()
                }
            })

        } catch(e: Exception) {

            JSONArray()

        }

    }

    fun getImageFromAssets(fileName: String): Bitmap {

        val file = assetManager.open(fileName)
        val bitmap = BitmapFactory.decodeStream(file)
        return bitmap

    }

}
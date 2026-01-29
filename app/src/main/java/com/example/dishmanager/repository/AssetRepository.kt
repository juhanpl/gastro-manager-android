package com.example.dishmanager.repository

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject

class AssetRepository(val context: Context) {

    fun getJsonFromAssets(fileName: String): JSONArray {

        val assetManager = context.assets

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

}
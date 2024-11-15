package com.paruyr.products.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.paruyr.products.model.Product
import com.paruyr.products.R

object JsonUtils {
    fun loadProductsFromJson(context: Context): List<Product> {
        val inputStream = context.resources.openRawResource(R.raw.products)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<Product>>() {}.type
        return Gson().fromJson(jsonString, type)
    }
}

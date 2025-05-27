package dev.daniza.portfoliowatcher.model.parser

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun <T> parseJsonToModel(jsonString: String): T {
    val type = object : TypeToken<T>() {}.type
    val parsedData: T = Gson().fromJson(jsonString, type)
    return parsedData
}
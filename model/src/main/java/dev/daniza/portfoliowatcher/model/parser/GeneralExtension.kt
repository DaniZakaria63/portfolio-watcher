package dev.daniza.portfoliowatcher.model.parser

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun Boolean?.isTrue(): Boolean = this == true

fun Float?.orZero(): Float = this ?: 0f

fun Double?.orZero(): Double = this ?: 0.0

inline fun <reified T> Gson.fromString(json: String): T {
    val type = object : TypeToken<T>() {}.type
    return fromJson(json, type)
}

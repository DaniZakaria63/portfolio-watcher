package dev.daniza.portfoliowatcher.remote.parser

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun String.toJsonRequestBody(): RequestBody {
    return this.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
}
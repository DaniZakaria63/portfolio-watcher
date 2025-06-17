package dev.daniza.portfoliowatcher.remote.parser

import dev.daniza.portfoliowatcher.model.tokenmetrics.TokenSearchModel

data class TokenSearchResponse(
    val success: Boolean,
    val message: String,
    val length: Int,
    val data: List<TokenSearchModel>
)
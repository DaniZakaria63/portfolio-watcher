package dev.daniza.portfoliowatcher.model.tokenmetrics

import com.google.gson.annotations.SerializedName

data class TokenSearchModel(
    @SerializedName("TOKEN_ID")
    val tokenId: Int,

    @SerializedName("TOKEN_NAME")
    val tokenName: String,

    @SerializedName("TOKEN_SYMBOL")
    val tokenSymbol: String,

    @SerializedName("EXCHANGE_LIST")
    val exchangeList: List<Exchange>,

    @SerializedName("CATEGORY_LIST")
    val categoryList: List<Category>,

    @SerializedName("TM_LINK")
    val tokenMetricsLink: String,

    @SerializedName("CONTRACT_ADDRESS")
    val contractAddress: Map<String, String>
) {

    data class Exchange(
        @SerializedName("exchange_id")
        val exchangeId: String,

        @SerializedName("exchange_name")
        val exchangeName: String
    )

    data class Category(
        @SerializedName("category_id")
        val categoryId: Int,

        @SerializedName("category_name")
        val categoryName: String,

        @SerializedName("category_slug")
        val categorySlug: String
    )

}


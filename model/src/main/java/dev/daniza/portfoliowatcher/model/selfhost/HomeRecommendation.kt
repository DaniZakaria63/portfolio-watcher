package dev.daniza.portfoliowatcher.model.selfhost

import com.google.gson.annotations.SerializedName

data class HomeRecommendation(
    @SerializedName("top_gainers" ) var topGainers : Recommendation? = Recommendation(),
    @SerializedName("top_losers"  ) var topLosers  : Recommendation?  = Recommendation()
)

data class Recommendation (
    @SerializedName("start"      ) var start      : Int?              = null,
    @SerializedName("count"      ) var count      : Int?              = null,
    @SerializedName("total"      ) var total      : Int?              = null,
    @SerializedName("quotes"     ) var quotes     : ArrayList<Quotes> = arrayListOf(),
    @SerializedName("useRecords" ) var useRecords : Boolean?          = null

){
    data class Quotes (
        @SerializedName("language"                          ) var language                          : String?           = null,
        @SerializedName("region"                            ) var region                            : String?           = null,
        @SerializedName("quoteType"                         ) var quoteType                         : String?           = null,
        @SerializedName("typeDisp"                          ) var typeDisp                          : String?           = null,
        @SerializedName("quoteSourceName"                   ) var quoteSourceName                   : String?           = null,
        @SerializedName("triggerable"                       ) var triggerable                       : Boolean?          = null,
        @SerializedName("customPriceAlertConfidence"        ) var customPriceAlertConfidence        : String?           = null,
        @SerializedName("currency"                          ) var currency                          : String?           = null,
        @SerializedName("regularMarketChangePercent"        ) var regularMarketChangePercent        : Double?           = null,
        @SerializedName("hasPrePostMarketData"              ) var hasPrePostMarketData              : Boolean?          = null,
        @SerializedName("firstTradeDateMilliseconds"        ) var firstTradeDateMilliseconds        : Long?              = null,
        @SerializedName("priceHint"                         ) var priceHint                         : Int?              = null,
        @SerializedName("regularMarketChange"               ) var regularMarketChange               : Double?           = null,
        @SerializedName("regularMarketTime"                 ) var regularMarketTime                 : Long?              = null,
        @SerializedName("regularMarketPrice"                ) var regularMarketPrice                : Double?           = null,
        @SerializedName("regularMarketDayHigh"              ) var regularMarketDayHigh              : Double?           = null,
        @SerializedName("regularMarketDayRange"             ) var regularMarketDayRange             : String?           = null,
        @SerializedName("regularMarketDayLow"               ) var regularMarketDayLow               : Double?           = null,
        @SerializedName("regularMarketVolume"               ) var regularMarketVolume               : Int?              = null,
        @SerializedName("regularMarketPreviousClose"        ) var regularMarketPreviousClose        : Double?           = null,
        @SerializedName("bidSize"                           ) var bidSize                           : Int?              = null,
        @SerializedName("askSize"                           ) var askSize                           : Int?              = null,
        @SerializedName("market"                            ) var market                            : String?           = null,
        @SerializedName("messageBoardId"                    ) var messageBoardId                    : String?           = null,
        @SerializedName("fullExchangeName"                  ) var fullExchangeName                  : String?           = null,
        @SerializedName("longName"                          ) var longName                          : String?           = null,
        @SerializedName("regularMarketOpen"                 ) var regularMarketOpen                 : Double?           = null,
        @SerializedName("averageDailyVolume3Month"          ) var averageDailyVolume3Month          : Int?              = null,
        @SerializedName("averageDailyVolume10Day"           ) var averageDailyVolume10Day           : Int?              = null,
        @SerializedName("exchange"                          ) var exchange                          : String?           = null,
        @SerializedName("fiftyTwoWeekHigh"                  ) var fiftyTwoWeekHigh                  : Double?           = null,
        @SerializedName("fiftyTwoWeekLow"                   ) var fiftyTwoWeekLow                   : Double?           = null,
        @SerializedName("dividendYield"                     ) var dividendYield                     : Double?           = null,
        @SerializedName("financialCurrency"                 ) var financialCurrency                 : String?           = null,
        @SerializedName("shortName"                         ) var shortName                         : String?           = null,
        @SerializedName("corporateActions"                  ) var corporateActions                  : List<CorporateActions>? = emptyList(),
        @SerializedName("fiftyTwoWeekLowChange"             ) var fiftyTwoWeekLowChange             : Double?           = null,
        @SerializedName("fiftyTwoWeekLowChangePercent"      ) var fiftyTwoWeekLowChangePercent      : Double?           = null,
        @SerializedName("fiftyTwoWeekRange"                 ) var fiftyTwoWeekRange                 : String?           = null,
        @SerializedName("fiftyTwoWeekHighChange"            ) var fiftyTwoWeekHighChange            : Double?              = null,
        @SerializedName("fiftyTwoWeekHighChangePercent"     ) var fiftyTwoWeekHighChangePercent     : Double?              = null,
        @SerializedName("fiftyTwoWeekChangePercent"         ) var fiftyTwoWeekChangePercent         : Double?           = null,
        @SerializedName("dividendDate"                      ) var dividendDate                      : Long?              = null,
        @SerializedName("earningsTimestampStart"            ) var earningsTimestampStart            : Long?              = null,
        @SerializedName("earningsTimestampEnd"              ) var earningsTimestampEnd              : Long?              = null,
        @SerializedName("earningsCallTimestampStart"        ) var earningsCallTimestampStart        : Long?              = null,
        @SerializedName("earningsCallTimestampEnd"          ) var earningsCallTimestampEnd          : Long?              = null,
        @SerializedName("isEarningsDateEstimate"            ) var isEarningsDateEstimate            : Boolean?          = null,
        @SerializedName("trailingAnnualDividendRate"        ) var trailingAnnualDividendRate        : Double?           = null,
        @SerializedName("trailingPE"                        ) var trailingPE                        : Double?           = null,
        @SerializedName("dividendRate"                      ) var dividendRate                      : Double?           = null,
        @SerializedName("trailingAnnualDividendYield"       ) var trailingAnnualDividendYield       : Double?           = null,
        @SerializedName("marketState"                       ) var marketState                       : String?           = null,
        @SerializedName("epsTrailingTwelveMonths"           ) var epsTrailingTwelveMonths           : Double?           = null,
        @SerializedName("sharesOutstanding"                 ) var sharesOutstanding                 : Long?              = null,
        @SerializedName("bookValue"                         ) var bookValue                         : Double?           = null,
        @SerializedName("fiftyDayAverage"                   ) var fiftyDayAverage                   : Double?           = null,
        @SerializedName("fiftyDayAverageChange"             ) var fiftyDayAverageChange             : Double?           = null,
        @SerializedName("fiftyDayAverageChangePercent"      ) var fiftyDayAverageChangePercent      : Double?           = null,
        @SerializedName("twoHundredDayAverage"              ) var twoHundredDayAverage              : Double?           = null,
        @SerializedName("twoHundredDayAverageChange"        ) var twoHundredDayAverageChange        : Double?           = null,
        @SerializedName("twoHundredDayAverageChangePercent" ) var twoHundredDayAverageChangePercent : Double?           = null,
        @SerializedName("priceToBook"                       ) var priceToBook                       : Double?           = null,
        @SerializedName("sourceInterval"                    ) var sourceInterval                    : Int?              = null,
        @SerializedName("exchangeDataDelayedBy"             ) var exchangeDataDelayedBy             : Int?              = null,
        @SerializedName("exchangeTimezoneName"              ) var exchangeTimezoneName              : String?           = null,
        @SerializedName("exchangeTimezoneShortName"         ) var exchangeTimezoneShortName         : String?           = null,
        @SerializedName("gmtOffSetMilliseconds"             ) var gmtOffSetMilliseconds             : Double?              = null,
        @SerializedName("esgPopulated"                      ) var esgPopulated                      : Boolean?          = null,
        @SerializedName("tradeable"                         ) var tradeable                         : Boolean?          = null,
        @SerializedName("cryptoTradeable"                   ) var cryptoTradeable                   : Boolean?          = null,
        @SerializedName("ask"                               ) var ask                               : Double?           = null,
        @SerializedName("marketCap"                         ) var marketCap                         : Double?              = null,
        @SerializedName("bid"                               ) var bid                               : Double?              = null,
        @SerializedName("displayName"                       ) var displayName                       : String?           = null,
        @SerializedName("symbol"                            ) var symbol                            : String?           = null

    ){
        var isFavorite: Boolean = false
            private set

        fun setFavorite(isFavorite: Boolean){
            this.isFavorite = isFavorite
        }

        data class CorporateActions(
            @SerializedName("header"  ) var header  : String? = null,
            @SerializedName("message" ) var message : String? = null,
            @SerializedName("meta"    ) var meta    : Meta?   = Meta()
        ){
            data class Meta(
                @SerializedName("eventType"   ) var eventType   : String? = null,
                @SerializedName("dateEpochMs" ) var dateEpochMs : Long?    = null,
                @SerializedName("amount"      ) var amount      : String? = null
            )
        }
    }
}
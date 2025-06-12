package dev.daniza.portfoliowatcher.ui.model

data class FavoriteHomeUIModel(
    val id: String,
    val name: String,
    val price: String,
    val changePercent: String,
    val timeframe: Timeframe = Timeframe.ONE_DAY,
    val chartData: List<Float> = emptyList(),
    val fullChartData: List<Double> = emptyList(),
) {
    enum class Timeframe {
        ONE_MINUTE,
        FIVE_MINUTES,
        ONE_HOUR,
        SIX_HOURS,
        ONE_DAY,
        ONE_MONTH,
        THREE_MONTHS,
        ONE_YEAR,
        ALL_TIME;

        override fun toString(): String {
            return when (this) {
                ONE_MINUTE -> "1 Minute"
                FIVE_MINUTES -> "5 Minutes"
                ONE_HOUR -> "1 Hour"
                SIX_HOURS -> "6 Hours"
                ONE_DAY -> "1 Day"
                ONE_MONTH -> "1 Month"
                THREE_MONTHS -> "3 Months"
                ONE_YEAR -> "1 Year"
                ALL_TIME -> "All Time"
            }
        }
    }
}

val sampleFavoriteHomeUIModel: List<FavoriteHomeUIModel> = listOf(
    FavoriteHomeUIModel(
        id = "1",
        name = "Bitcoin",
        price = "$42,000",
        changePercent = "+3.5%",
        chartData = listOf(41000f, 41500f, 42000f, 41800f, 42000f)
    ),
    FavoriteHomeUIModel(
        id = "2",
        name = "Ethereum",
        price = "$2,800",
        changePercent = "-1.2%",
        chartData = listOf(2850f, 2820f, 2800f, 2810f, 2800f)
    ),
    FavoriteHomeUIModel(
        id = "3",
        name = "Solana",
        price = "$110",
        changePercent = "+0.8%",
        chartData = listOf(108f, 109f, 110f, 111f, 110f)
    ),
    FavoriteHomeUIModel(
        id = "4",
        name = "Cardano",
        price = "$0.45",
        changePercent = "+2.1%",
        chartData = listOf(0.43f, 0.44f, 0.45f, 0.46f, 0.45f)
    ),
    FavoriteHomeUIModel(
        id = "5",
        name = "Dogecoin",
        price = "$0.075",
        changePercent = "-0.5%",
        chartData = listOf(0.076f, 0.075f, 0.074f, 0.075f, 0.075f)
    )
)
package dev.daniza.portfoliowatcher.remote.selfhost

import com.google.gson.Gson
import dev.daniza.portfoliowatcher.model.parser.fromString
import dev.daniza.portfoliowatcher.model.selfhost.HomeDailyChartModel
import dev.daniza.portfoliowatcher.model.selfhost.HomeDailySummaryModel
import dev.daniza.portfoliowatcher.model.session.UserSession
import dev.daniza.portfoliowatcher.remote.parser.SelfHostResponse
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

class SelfHostService(
    private val selfHostRemoteEndpoint: SelfHostRemoteEndpoint
) : SelfHostRemote {
    override suspend fun checkTokenUserSession(token: String): SelfHostResponse<UserSession> {
        val jsonToken = JSONObject().apply {
            put("token", token)
        }.toString()
        return selfHostRemoteEndpoint.checkTokenUserSession(jsonToken.toRequestBody())
    }

    override suspend fun getHomeDailySummaryData(
        token: String,
        symbols: List<String>
    ): SelfHostResponse<List<HomeDailySummaryModel>> {
        val jsonBody = JSONObject().apply {
            put("token", token)
            put("symbol", JSONArray(symbols))
        }.toString()
//        return selfHostRemoteEndpoint.getHomeDailySummaryData(jsonBody.toRequestBody())
        val response = """
            {
              "success": true,
              "data": [
                {
                  "candles": [
                    {
                      "close_price": 278.3702
                    },
                    {
                      "close_price": 278.45
                    },
                    {
                      "close_price": 278.22
                    },
                    {
                      "close_price": 278.26
                    },
                    {
                      "close_price": 278.86
                    },
                    {
                      "close_price": 276.65
                    },
                    {
                      "close_price": 276.465
                    },
                    {
                      "close_price": 276.2701
                    },
                    {
                      "close_price": 278.43
                    },
                    {
                      "close_price": 278.58
                    },
                    {
                      "close_price": 278.76
                    },
                    {
                      "close_price": 278.73
                    },
                    {
                      "close_price": 278.61
                    },
                    {
                      "close_price": 277.9
                    },
                    {
                      "close_price": 277.9625
                    },
                    {
                      "close_price": 277.7715
                    },
                    {
                      "close_price": 277.88
                    },
                    {
                      "close_price": 277.47
                    },
                    {
                      "close_price": 278.43
                    },
                    {
                      "close_price": 278.12
                    },
                    {
                      "close_price": 278.88
                    },
                    {
                      "close_price": 279.3
                    },
                    {
                      "close_price": 278.08
                    },
                    {
                      "close_price": 277.1024
                    },
                    {
                      "close_price": 277.6
                    },
                    {
                      "close_price": 278.12
                    },
                    {
                      "close_price": 278.11
                    },
                    {
                      "close_price": 278.1
                    },
                    {
                      "close_price": 278.16
                    },
                    {
                      "close_price": 277.4999
                    },
                    {
                      "close_price": 276.9588
                    },
                    {
                      "close_price": 276.89
                    },
                    {
                      "close_price": 276.75
                    },
                    {
                      "close_price": 276.89
                    },
                    {
                      "close_price": 277.51
                    },
                    {
                      "close_price": 278.3
                    },
                    {
                      "close_price": 278.53
                    },
                    {
                      "close_price": 278.66
                    },
                    {
                      "close_price": 278.839
                    },
                    {
                      "close_price": 279.375
                    },
                    {
                      "close_price": 274.63
                    },
                    {
                      "close_price": 274.25
                    },
                    {
                      "close_price": 274.79
                    },
                    {
                      "close_price": 275.32
                    },
                    {
                      "close_price": 274.72
                    },
                    {
                      "close_price": 275.73
                    },
                    {
                      "close_price": 275.88
                    },
                    {
                      "close_price": 275.86
                    },
                    {
                      "close_price": 276.0495
                    },
                    {
                      "close_price": 275.8
                    },
                    {
                      "close_price": 276.08
                    },
                    {
                      "close_price": 276.03
                    },
                    {
                      "close_price": 275.2
                    },
                    {
                      "close_price": 275.01
                    },
                    {
                      "close_price": 273.79
                    },
                    {
                      "close_price": 273.505
                    },
                    {
                      "close_price": 271.975
                    },
                    {
                      "close_price": 272.01
                    },
                    {
                      "close_price": 271.29
                    },
                    {
                      "close_price": 270.97
                    },
                    {
                      "close_price": 271
                    },
                    {
                      "close_price": 271.35
                    },
                    {
                      "close_price": 271.14
                    },
                    {
                      "close_price": 271.02
                    },
                    {
                      "close_price": 271.15
                    },
                    {
                      "close_price": 271.5
                    },
                    {
                      "close_price": 272.17
                    },
                    {
                      "close_price": 272.48
                    },
                    {
                      "close_price": 271.23
                    },
                    {
                      "close_price": 270.84
                    },
                    {
                      "close_price": 270.88
                    },
                    {
                      "close_price": 268.29
                    },
                    {
                      "close_price": 265.58
                    },
                    {
                      "close_price": 266.08
                    },
                    {
                      "close_price": 266.11
                    },
                    {
                      "close_price": 266
                    },
                    {
                      "close_price": 266.14
                    },
                    {
                      "close_price": 267.1
                    },
                    {
                      "close_price": 266.32
                    },
                    {
                      "close_price": 266.15
                    },
                    {
                      "close_price": 265.25
                    },
                    {
                      "close_price": 266.38
                    },
                    {
                      "close_price": 267.805
                    },
                    {
                      "close_price": 266.94
                    },
                    {
                      "close_price": 267.48
                    },
                    {
                      "close_price": 269.349
                    },
                    {
                      "close_price": 273.54
                    },
                    {
                      "close_price": 275
                    },
                    {
                      "close_price": 270.29
                    },
                    {
                      "close_price": 270.17
                    },
                    {
                      "close_price": 269.4
                    },
                    {
                      "close_price": 269.67
                    },
                    {
                      "close_price": 269.01
                    },
                    {
                      "close_price": 269.59
                    },
                    {
                      "close_price": 269
                    },
                    {
                      "close_price": 267.89
                    },
                    {
                      "close_price": 268.73
                    },
                    {
                      "close_price": 268.5201
                    },
                    {
                      "close_price": 269.73
                    },
                    {
                      "close_price": 270.8
                    }
                  ],
                  "current_price": 278.3702,
                  "gain": -0.07979999999997744,
                  "name": "AAPL",
                  "price_opening": 277.98,
                  "symbol": "AAPL"
                },
                {
                  "candles": [
                    {
                      "close_price": 319.89
                    },
                    {
                      "close_price": 320.28
                    },
                    {
                      "close_price": 320.16
                    },
                    {
                      "close_price": 319.91
                    },
                    {
                      "close_price": 320.17
                    },
                    {
                      "close_price": 318.23
                    },
                    {
                      "close_price": 318.85
                    },
                    {
                      "close_price": 318.68
                    },
                    {
                      "close_price": 323.93
                    },
                    {
                      "close_price": 322.97
                    },
                    {
                      "close_price": 322.84
                    },
                    {
                      "close_price": 323.93
                    },
                    {
                      "close_price": 323.51
                    },
                    {
                      "close_price": 320.5
                    },
                    {
                      "close_price": 320.72
                    },
                    {
                      "close_price": 320.75
                    },
                    {
                      "close_price": 320.91
                    },
                    {
                      "close_price": 320.01
                    },
                    {
                      "close_price": 319.09
                    },
                    {
                      "close_price": 318.82
                    },
                    {
                      "close_price": 318.375
                    },
                    {
                      "close_price": 319.39
                    },
                    {
                      "close_price": 317.915
                    },
                    {
                      "close_price": 320.795
                    },
                    {
                      "close_price": 320.06
                    },
                    {
                      "close_price": 327.05
                    },
                    {
                      "close_price": 328.73
                    },
                    {
                      "close_price": 329.33
                    },
                    {
                      "close_price": 328.27
                    },
                    {
                      "close_price": 325.2985
                    },
                    {
                      "close_price": 324.7
                    },
                    {
                      "close_price": 324.78
                    },
                    {
                      "close_price": 322.83
                    },
                    {
                      "close_price": 323.39
                    },
                    {
                      "close_price": 322.96
                    },
                    {
                      "close_price": 322.79
                    },
                    {
                      "close_price": 321.98
                    },
                    {
                      "close_price": 320.23
                    },
                    {
                      "close_price": 321.17
                    },
                    {
                      "close_price": 325.98
                    },
                    {
                      "close_price": 331.06
                    },
                    {
                      "close_price": 332.03
                    },
                    {
                      "close_price": 331.22
                    },
                    {
                      "close_price": 331.08
                    },
                    {
                      "close_price": 329.76
                    },
                    {
                      "close_price": 326.95
                    },
                    {
                      "close_price": 326.51
                    },
                    {
                      "close_price": 325.39
                    },
                    {
                      "close_price": 321.2
                    },
                    {
                      "close_price": 318.38
                    },
                    {
                      "close_price": 317.97
                    },
                    {
                      "close_price": 316.42
                    },
                    {
                      "close_price": 316.78
                    },
                    {
                      "close_price": 312.13
                    },
                    {
                      "close_price": 314.8
                    },
                    {
                      "close_price": 316.26
                    },
                    {
                      "close_price": 311.0879
                    },
                    {
                      "close_price": 310.61
                    },
                    {
                      "close_price": 308.39
                    },
                    {
                      "close_price": 306.4
                    },
                    {
                      "close_price": 306.94
                    },
                    {
                      "close_price": 302.5
                    },
                    {
                      "close_price": 301.7
                    },
                    {
                      "close_price": 300.39
                    },
                    {
                      "close_price": 300.07
                    },
                    {
                      "close_price": 299.74
                    },
                    {
                      "close_price": 301.66
                    },
                    {
                      "close_price": 300.42
                    },
                    {
                      "close_price": 296.66
                    },
                    {
                      "close_price": 298.64
                    },
                    {
                      "close_price": 298.84
                    },
                    {
                      "close_price": 299.12
                    },
                    {
                      "close_price": 296.48
                    },
                    {
                      "close_price": 292.72
                    },
                    {
                      "close_price": 290.83
                    },
                    {
                      "close_price": 290.27
                    },
                    {
                      "close_price": 289.02
                    },
                    {
                      "close_price": 289.68
                    },
                    {
                      "close_price": 288.2595
                    },
                    {
                      "close_price": 287.38
                    },
                    {
                      "close_price": 287.8
                    },
                    {
                      "close_price": 289.59
                    },
                    {
                      "close_price": 292.9038
                    },
                    {
                      "close_price": 292.1808
                    },
                    {
                      "close_price": 293.87
                    },
                    {
                      "close_price": 294.57
                    },
                    {
                      "close_price": 304.0601
                    },
                    {
                      "close_price": 303.26
                    },
                    {
                      "close_price": 304.15
                    },
                    {
                      "close_price": 300.1
                    },
                    {
                      "close_price": 298.68
                    },
                    {
                      "close_price": 298.75
                    },
                    {
                      "close_price": 297.95
                    },
                    {
                      "close_price": 299.52
                    },
                    {
                      "close_price": 299.9
                    },
                    {
                      "close_price": 297.15
                    },
                    {
                      "close_price": 296.06
                    },
                    {
                      "close_price": 292.8
                    },
                    {
                      "close_price": 294.02
                    },
                    {
                      "close_price": 294.08
                    }
                  ],
                  "current_price": 319.89,
                  "gain": -0.38999999999998636,
                  "name": "GOOGL",
                  "price_opening": 323.15,
                  "symbol": "GOOGL"
                },
                {
                  "candles": [
                    {
                      "close_price": 233.11
                    },
                    {
                      "close_price": 233.18
                    },
                    {
                      "close_price": 233.3
                    },
                    {
                      "close_price": 232.93
                    },
                    {
                      "close_price": 233.23
                    },
                    {
                      "close_price": 232.67
                    },
                    {
                      "close_price": 231.96
                    },
                    {
                      "close_price": 231.48
                    },
                    {
                      "close_price": 231.3
                    },
                    {
                      "close_price": 230.98
                    },
                    {
                      "close_price": 231.07
                    },
                    {
                      "close_price": 230.98
                    },
                    {
                      "close_price": 230.86
                    },
                    {
                      "close_price": 229.555
                    },
                    {
                      "close_price": 229.435
                    },
                    {
                      "close_price": 229.48
                    },
                    {
                      "close_price": 229.31
                    },
                    {
                      "close_price": 229.09
                    },
                    {
                      "close_price": 229.25
                    },
                    {
                      "close_price": 229.56
                    },
                    {
                      "close_price": 230.5485
                    },
                    {
                      "close_price": 231.41
                    },
                    {
                      "close_price": 230.62
                    },
                    {
                      "close_price": 229.7975
                    },
                    {
                      "close_price": 231.85
                    },
                    {
                      "close_price": 231.28
                    },
                    {
                      "close_price": 231.07
                    },
                    {
                      "close_price": 231.18
                    },
                    {
                      "close_price": 230.8
                    },
                    {
                      "close_price": 230.0499
                    },
                    {
                      "close_price": 229.87
                    },
                    {
                      "close_price": 229.86
                    },
                    {
                      "close_price": 229.86
                    },
                    {
                      "close_price": 229.58
                    },
                    {
                      "close_price": 229.78
                    },
                    {
                      "close_price": 230.12
                    },
                    {
                      "close_price": 229.605
                    },
                    {
                      "close_price": 229.15
                    },
                    {
                      "close_price": 227.6001
                    },
                    {
                      "close_price": 227.75
                    },
                    {
                      "close_price": 225.34
                    },
                    {
                      "close_price": 227.01
                    },
                    {
                      "close_price": 226.8
                    },
                    {
                      "close_price": 226.26
                    },
                    {
                      "close_price": 226.13
                    },
                    {
                      "close_price": 226.3
                    },
                    {
                      "close_price": 226.3
                    },
                    {
                      "close_price": 226.65
                    },
                    {
                      "close_price": 226.43
                    },
                    {
                      "close_price": 226.07
                    },
                    {
                      "close_price": 226.105
                    },
                    {
                      "close_price": 225.185
                    },
                    {
                      "close_price": 225.005
                    },
                    {
                      "close_price": 224.63
                    },
                    {
                      "close_price": 225.39
                    },
                    {
                      "close_price": 225.7766
                    },
                    {
                      "close_price": 222.63
                    },
                    {
                      "close_price": 222.39
                    },
                    {
                      "close_price": 222.4
                    },
                    {
                      "close_price": 221.75
                    },
                    {
                      "close_price": 222.43
                    },
                    {
                      "close_price": 221.4999
                    },
                    {
                      "close_price": 221.26
                    },
                    {
                      "close_price": 221.4
                    },
                    {
                      "close_price": 220.879
                    },
                    {
                      "close_price": 220.67
                    },
                    {
                      "close_price": 220.92
                    },
                    {
                      "close_price": 219.39
                    },
                    {
                      "close_price": 219.42
                    },
                    {
                      "close_price": 219.795
                    },
                    {
                      "close_price": 215.688
                    },
                    {
                      "close_price": 217.11
                    },
                    {
                      "close_price": 218.4204
                    },
                    {
                      "close_price": 218.31
                    },
                    {
                      "close_price": 217.25
                    },
                    {
                      "close_price": 217.71
                    },
                    {
                      "close_price": 217.5
                    },
                    {
                      "close_price": 217.85
                    },
                    {
                      "close_price": 217.2
                    },
                    {
                      "close_price": 216.9
                    },
                    {
                      "close_price": 216.81
                    },
                    {
                      "close_price": 217.15
                    },
                    {
                      "close_price": 219.945
                    },
                    {
                      "close_price": 218.76
                    },
                    {
                      "close_price": 219.0893
                    },
                    {
                      "close_price": 220.7
                    },
                    {
                      "close_price": 225.97
                    },
                    {
                      "close_price": 226.2
                    },
                    {
                      "close_price": 227.73
                    },
                    {
                      "close_price": 226.78
                    },
                    {
                      "close_price": 226.47
                    },
                    {
                      "close_price": 226.63
                    },
                    {
                      "close_price": 225.94
                    },
                    {
                      "close_price": 226.89
                    },
                    {
                      "close_price": 226.8
                    },
                    {
                      "close_price": 225.3
                    },
                    {
                      "close_price": 223.9
                    },
                    {
                      "close_price": 222.69
                    },
                    {
                      "close_price": 221.5
                    },
                    {
                      "close_price": 221.84
                    }
                  ],
                  "current_price": 233.11,
                  "gain": -0.06999999999999318,
                  "name": "AMZN",
                  "price_opening": 230.19,
                  "symbol": "AMZN"
                },
                {
                  "candles": [
                    {
                      "close_price": 430.245
                    },
                    {
                      "close_price": 430.15
                    },
                    {
                      "close_price": 430.2192
                    },
                    {
                      "close_price": 429.86
                    },
                    {
                      "close_price": 430.18
                    },
                    {
                      "close_price": 429.34
                    },
                    {
                      "close_price": 430.18
                    },
                    {
                      "close_price": 429.0974
                    },
                    {
                      "close_price": 427.91
                    },
                    {
                      "close_price": 428.92
                    },
                    {
                      "close_price": 428.34
                    },
                    {
                      "close_price": 428.55
                    },
                    {
                      "close_price": 427.92
                    },
                    {
                      "close_price": 426.57
                    },
                    {
                      "close_price": 426.15
                    },
                    {
                      "close_price": 426.1
                    },
                    {
                      "close_price": 426.65
                    },
                    {
                      "close_price": 426.5
                    },
                    {
                      "close_price": 424.23
                    },
                    {
                      "close_price": 423.1922
                    },
                    {
                      "close_price": 422.48
                    },
                    {
                      "close_price": 420.66
                    },
                    {
                      "close_price": 419.29
                    },
                    {
                      "close_price": 417.455
                    },
                    {
                      "close_price": 425.17
                    },
                    {
                      "close_price": 425.53
                    },
                    {
                      "close_price": 424.33
                    },
                    {
                      "close_price": 423.51
                    },
                    {
                      "close_price": 421.78
                    },
                    {
                      "close_price": 419.1001
                    },
                    {
                      "close_price": 418.6
                    },
                    {
                      "close_price": 418.76
                    },
                    {
                      "close_price": 418.8
                    },
                    {
                      "close_price": 419.31
                    },
                    {
                      "close_price": 417.34
                    },
                    {
                      "close_price": 417.06
                    },
                    {
                      "close_price": 417.51
                    },
                    {
                      "close_price": 414.24
                    },
                    {
                      "close_price": 411.0588
                    },
                    {
                      "close_price": 412.3699
                    },
                    {
                      "close_price": 415.27
                    },
                    {
                      "close_price": 418.69
                    },
                    {
                      "close_price": 415.43
                    },
                    {
                      "close_price": 415.21
                    },
                    {
                      "close_price": 414.03
                    },
                    {
                      "close_price": 418.75
                    },
                    {
                      "close_price": 418.8799
                    },
                    {
                      "close_price": 419.5009
                    },
                    {
                      "close_price": 418.53
                    },
                    {
                      "close_price": 417.52
                    },
                    {
                      "close_price": 417.54
                    },
                    {
                      "close_price": 419.4169
                    },
                    {
                      "close_price": 419.0585
                    },
                    {
                      "close_price": 418.9661
                    },
                    {
                      "close_price": 414.86
                    },
                    {
                      "close_price": 411.7897
                    },
                    {
                      "close_price": 398.91
                    },
                    {
                      "close_price": 399.5
                    },
                    {
                      "close_price": 398.37
                    },
                    {
                      "close_price": 397.65
                    },
                    {
                      "close_price": 398.31
                    },
                    {
                      "close_price": 393.84
                    },
                    {
                      "close_price": 393.96
                    },
                    {
                      "close_price": 393.3
                    },
                    {
                      "close_price": 393.41
                    },
                    {
                      "close_price": 391.2
                    },
                    {
                      "close_price": 397.31
                    },
                    {
                      "close_price": 399.3485
                    },
                    {
                      "close_price": 398.95
                    },
                    {
                      "close_price": 398.79
                    },
                    {
                      "close_price": 385.83
                    },
                    {
                      "close_price": 395.1601
                    },
                    {
                      "close_price": 400.83
                    },
                    {
                      "close_price": 398.46
                    },
                    {
                      "close_price": 396.08
                    },
                    {
                      "close_price": 398.1
                    },
                    {
                      "close_price": 395.51
                    },
                    {
                      "close_price": 397.63
                    },
                    {
                      "close_price": 397.591
                    },
                    {
                      "close_price": 393.625
                    },
                    {
                      "close_price": 392.976
                    },
                    {
                      "close_price": 395.08
                    },
                    {
                      "close_price": 404.3884
                    },
                    {
                      "close_price": 399.7374
                    },
                    {
                      "close_price": 404.115
                    },
                    {
                      "close_price": 408.76
                    },
                    {
                      "close_price": 423.63
                    },
                    {
                      "close_price": 425.728
                    },
                    {
                      "close_price": 412.93
                    },
                    {
                      "close_price": 413.09
                    },
                    {
                      "close_price": 410.44
                    },
                    {
                      "close_price": 411.47
                    },
                    {
                      "close_price": 409.77
                    },
                    {
                      "close_price": 410.5
                    },
                    {
                      "close_price": 410.95
                    },
                    {
                      "close_price": 407.56
                    },
                    {
                      "close_price": 408.11
                    },
                    {
                      "close_price": 404.09
                    },
                    {
                      "close_price": 402.1912
                    },
                    {
                      "close_price": 402.99
                    }
                  ],
                  "current_price": 430.245,
                  "gain": 0.09500000000002728,
                  "name": "TSLA",
                  "price_opening": 427.3,
                  "symbol": "TSLA"
                }
              ]
            }
        """.trimIndent()
        return Gson().fromString<SelfHostResponse<List<HomeDailySummaryModel>>>(response)
    }

    override suspend fun getHomeDailyChartData(
        token: String,
        symbol: String,
        range: String
    ): SelfHostResponse<HomeDailyChartModel> {
        val body = JSONObject().apply {
            put("token", token)
            put("symbol", JSONArray().apply {
                put(0, symbol) }
            )
            put("function", range)
        }.toString()
        return selfHostRemoteEndpoint.getHomeDailyChartData(body.toRequestBody())
    }
}
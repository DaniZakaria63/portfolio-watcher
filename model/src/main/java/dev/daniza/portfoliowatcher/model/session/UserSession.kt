package dev.daniza.portfoliowatcher.model.session

import com.google.gson.annotations.SerializedName

data class UserSession(
    @SerializedName("token")
    val token: String = "",
    @SerializedName("created_at")
    val created_at: Long? = null, /*FROM API*/
    @SerializedName("updated_at")
    val updated_at: Long? = null, /*FROM CLIENT*/
    @SerializedName("isActive")
    val isActive: Boolean? = false /*FROM API->actually means IsNewSession*/
){
    companion object{
        const val NAME :String = "USER_SESSION"
    }
}
package dev.daniza.portfoliowatcher.model.session

import com.google.gson.annotations.SerializedName

data class UserSession(
    @SerializedName("token")
    val token: String = "",
    @SerializedName("created_at")
    val created_at: Long? = null, /*FROM API*/
    @SerializedName("updated_at")
    val updated_at: Long? = null, /*FROM CLIENT*/
    @SerializedName("isNewUpdate")
    val isNewUpdate: Boolean? = false /*FROM API*/
){
    companion object{
        const val NAME :String = "USER_SESSION"
    }
}
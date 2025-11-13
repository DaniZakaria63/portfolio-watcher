package dev.daniza.portfoliowatcher.model.session

data class UserSession(
    val token: String = "",
    val created_at: Long? = null, /*FROM API*/
    val updated_at: Long? = null, /*FROM CLIENT*/
){
    companion object{
        const val NAME :String = "USER_SESSION"
    }
}
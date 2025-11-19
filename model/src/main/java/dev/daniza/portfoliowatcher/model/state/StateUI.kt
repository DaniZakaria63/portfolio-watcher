package dev.daniza.portfoliowatcher.model.state

/**
 * Simple UI state holder used to represent loading / data / error states in Compose screens.
 */
data class StateUI<T>(
    val loading: Loading = Loading.LOADING,
    val data: T? = null,
    val error: String? = null
){
    enum class Loading {
        LOADING,
        DONE,
        ERROR
    }
}
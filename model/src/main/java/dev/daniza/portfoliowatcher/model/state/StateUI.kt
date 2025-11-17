package dev.daniza.portfoliowatcher.model.state

/**
 * Simple UI state holder used to represent loading / data / error states in Compose screens.
 */
data class StateUI<T>(
    val loading: Boolean = false,
    val data: T? = null,
    val error: String? = null
)
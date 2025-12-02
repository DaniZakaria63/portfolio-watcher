package dev.daniza.portfoliowatcher.model.state

/**
 * Simple UI state holder used to represent loading / data / error states in Compose screens.
 */
sealed interface StateUI<out T> {
    object Loading : StateUI<Nothing>
    data class Data<T>(val value: T) : StateUI<T>
    data class Error(val throwable: Throwable) : StateUI<Nothing>
}
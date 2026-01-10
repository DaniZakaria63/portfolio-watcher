package dev.daniza.portfoliowatcher.model.exception

class NoDataException(
    message: String = "No data received from server"
) : Exception(message)
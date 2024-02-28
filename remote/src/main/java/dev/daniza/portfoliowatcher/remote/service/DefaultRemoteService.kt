package dev.daniza.portfoliowatcher.remote.service

import dev.daniza.portfoliowatcher.remote.endpoint.RemoteEndpoint
import javax.inject.Inject

class DefaultRemoteService @Inject constructor(
    val remoteEndpoint: RemoteEndpoint
) : RemoteService {

}
package dev.daniza.portfoliowatcher.remote

import javax.inject.Inject

class DefaultRemoteService @Inject constructor(
    val remoteEndpoint: RemoteEndpoint
) : RemoteService{

}
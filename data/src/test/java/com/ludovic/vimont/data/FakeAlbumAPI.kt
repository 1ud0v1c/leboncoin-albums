package com.ludovic.vimont.data

import com.ludovic.vimont.data.network.AlbumAPI
import com.ludovic.vimont.domain.entities.Album
import okhttp3.ResponseBody
import retrofit2.Response

class FakeAlbumAPI: AlbumAPI {
    companion object {
        const val SERVER_ERROR_MESSAGE = "Internal Server error"
    }

    override suspend fun getAlbums(): Response<List<Album>> {
        val responseBody: ResponseBody = ResponseBody.create(null, SERVER_ERROR_MESSAGE)
        return Response.error(400, responseBody)
    }
}
package com.ludovic.vimont.data

import com.ludovic.vimont.data.network.AlbumAPI
import com.ludovic.vimont.domain.entities.Album
import okhttp3.ResponseBody
import retrofit2.Response

class FakeAlbumAPI: AlbumAPI {
    override suspend fun getAlbums(): Response<List<Album>> {
        val responseBody: ResponseBody = ResponseBody.create(null, "Internal Server error")
        return Response.error(400, responseBody)
    }
}
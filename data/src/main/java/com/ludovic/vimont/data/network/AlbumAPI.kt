package com.ludovic.vimont.data.network

import com.ludovic.vimont.domain.entities.Album
import retrofit2.Response
import retrofit2.http.GET

interface AlbumAPI {
    companion object {
        const val BASE_URL = "https://static.leboncoin.fr/"
    }

    @GET("img/shared/technical-test.json")
    suspend fun getAlbums(): Response<List<Album>>
}
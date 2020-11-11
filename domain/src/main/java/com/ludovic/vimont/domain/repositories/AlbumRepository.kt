package com.ludovic.vimont.domain.repositories

import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album

interface AlbumRepository {
    suspend fun getAlbums(isRefreshNeeded: Boolean = false): StateData<List<Album>>

    suspend fun getAlbum(albumId: Int): StateData<Album>
}
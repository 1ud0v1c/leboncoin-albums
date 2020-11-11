package com.ludovic.vimont.domain.repositories

import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album

interface AlbumRepository {
    fun getAlbums(isRefreshNeeded: Boolean = false): StateData<List<Album>>

    fun getAlbum(albumId: Int): StateData<Album>
}
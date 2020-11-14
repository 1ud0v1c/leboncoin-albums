package com.ludovic.vimont.domain

import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.domain.repositories.AlbumRepository

class FakeAlbumRepository: AlbumRepository {
    companion object {
        const val ALBUMS_LIST_SIZE: Int = 30
    }
    var hasNetworkErrorOccurred: Boolean = false
    private val albumsList: List<Album> = AlbumMockHelper.buildAlbums(ALBUMS_LIST_SIZE)

    override suspend fun getAlbums(isRefreshNeeded: Boolean): StateData<List<Album>> {
        if (hasNetworkErrorOccurred) {
            return StateData.error("An network error occurred")
        }
        return StateData.success(albumsList)
    }

    override suspend fun getAlbum(albumId: Int): StateData<Album> {
        val foundElement: Album? = albumsList.find { album: Album ->
            album.id == albumId
        }
        foundElement?.let { foundAlbum: Album ->
            return StateData.success(foundAlbum)
        }
        return StateData.error("The album couldn't been found...")
    }
}
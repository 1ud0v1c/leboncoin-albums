package com.ludovic.vimont.domain

import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.domain.repositories.AlbumRepository

class FakeAlbumRepository: AlbumRepository {
    companion object {
        const val ALBUMS_LIST_SIZE: Int = 30
        const val GET_NETWORK_ERROR_MESSAGE = "An network error occurred"
        const val GET_ALBUM_ERROR_MESSAGE = "The album couldn't been found..."
    }
    var hasNetworkErrorOccurred: Boolean = false
    private val albumsList: List<Album> = AlbumMockHelper.buildAlbums(ALBUMS_LIST_SIZE)

    override suspend fun getAlbums(isRefreshNeeded: Boolean): StateData<List<Album>> {
        if (hasNetworkErrorOccurred) {
            return StateData.Error(GET_NETWORK_ERROR_MESSAGE)
        }
        return StateData.Success(albumsList)
    }

    override suspend fun getAlbum(albumId: Int): StateData<Album> {
        val foundElement: Album? = albumsList.find { album: Album ->
            album.id == albumId
        }
        foundElement?.let { foundAlbum: Album ->
            return StateData.Success(foundAlbum)
        }
        return StateData.Error(GET_ALBUM_ERROR_MESSAGE)
    }
}
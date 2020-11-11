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

    override fun getAlbums(isRefreshNeeded: Boolean): StateData<List<Album>> {
        if (hasNetworkErrorOccurred) {
            return StateData.error("An network error occurred")
        }
        return StateData.success(albumsList)
    }

    override fun getAlbum(albumId: Int): StateData<Album> {
        val foundedElement: Album? = albumsList.find { album: Album ->
            album.id == albumId
        }
        foundedElement?.let { foundedAlbum: Album ->
            return StateData.success(foundedAlbum)
        }
        return StateData.error("The album couldn't been found...")
    }
}
package com.ludovic.vimont.leboncoinalbums.screens

import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.domain.repositories.AlbumRepository
import java.util.*
import kotlin.collections.ArrayList

class FakeAlbumRepositoryImpl(albumListSize: Int = 5) : AlbumRepository {
    private val albums = ArrayList<Album>()
    private val errorMessage = "The album couldn't been found..."

    init {
        for (i: Int in 1 until albumListSize + 1 step 1) {
            albums.add(buildAlbum(i))
        }
    }

    private fun buildAlbum(id: Int): Album {
        return Album(
            id,
            42,
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        )
    }

    override suspend fun getAlbums(isRefreshNeeded: Boolean): StateData<List<Album>> {
        return StateData.Success(albums)
    }

    override suspend fun getAlbum(albumId: Int): StateData<Album> {
        val foundElement: Album? = albums.find { album: Album ->
            album.id == albumId
        }
        foundElement?.let { foundAlbum: Album ->
            return StateData.Success(foundAlbum)
        }
        return StateData.Error(errorMessage)
    }

    fun count(): Int {
        return albums.size
    }

    fun getAlbums(): List<Album> {
        return albums
    }

    fun lastAlbum(): Album {
        return albums.last()
    }

    fun errorMessage(): String {
        return errorMessage
    }
}
package com.ludovic.vimont.domain

import com.ludovic.vimont.domain.entities.Album
import java.util.*
import kotlin.collections.ArrayList

object AlbumMockHelper {
    fun buildAlbums(listSize: Int): List<Album> {
        val albumsList = ArrayList<Album>()
        for (index: Int in 1..listSize step 1) {
            albumsList.add(buildAlbum(index))
        }
        return albumsList
    }

    fun buildAlbum(id: Int): Album {
        return Album(
            id,
            42,
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        )
    }
}
package com.ludovic.vimont.data.source

import com.ludovic.vimont.data.model.Album

typealias AlbumEntity = com.ludovic.vimont.domain.entities.Album

fun Album.asEntity() = AlbumEntity(
        id = id,
        albumId = albumId,
        title = title,
        url = url,
        thumbnailUrl = thumbnailUrl
)

fun AlbumEntity.asData() = Album(
        id = id,
        albumId = albumId,
        title = title,
        url = url,
        thumbnailUrl = thumbnailUrl
)

fun List<Album>.asEntity(): List<AlbumEntity> = map(Album::asEntity)

fun List<AlbumEntity>.asData(): List<Album> = map(AlbumEntity::asData)
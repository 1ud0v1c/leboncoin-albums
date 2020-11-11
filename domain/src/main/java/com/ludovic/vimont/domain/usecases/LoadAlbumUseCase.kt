package com.ludovic.vimont.domain.usecases

import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.domain.repositories.AlbumRepository

class LoadAlbumUseCase(
    private val albumRepository: AlbumRepository
): UseCase<Int, StateData<Album>> {
    override suspend fun execute(input: Int): StateData<Album> {
        return albumRepository.getAlbum(input)
    }
}
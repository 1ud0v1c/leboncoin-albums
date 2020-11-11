package com.ludovic.vimont.domain.usecases

import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.domain.repositories.AlbumRepository

class LoadAlbumsListUseCase(
    private val albumRepository: AlbumRepository
): UseCase<Boolean, StateData<List<Album>>> {
    override fun execute(input: Boolean): StateData<List<Album>> {
        return albumRepository.getAlbums(input)
    }
}
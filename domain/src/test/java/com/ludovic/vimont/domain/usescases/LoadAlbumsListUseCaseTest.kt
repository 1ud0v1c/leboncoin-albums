package com.ludovic.vimont.domain.usescases

import com.ludovic.vimont.domain.FakeAlbumRepository
import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.domain.usecases.LoadAlbumsListUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LoadAlbumsListUseCaseTest {
    private val albumRepository = FakeAlbumRepository()
    private lateinit var loadAlbumsListUseCase: LoadAlbumsListUseCase

    @Before
    fun setUp() {
        loadAlbumsListUseCase = LoadAlbumsListUseCase(albumRepository)
    }

    @Test
    fun testGetListOfAlbums() = runBlocking {
        val stateData: StateData<List<Album>> = loadAlbumsListUseCase.execute(false)
        Assert.assertTrue(stateData is StateData.Success)
        if (stateData is StateData.Success) {
            Assert.assertEquals(FakeAlbumRepository.ALBUMS_LIST_SIZE, stateData.data.size)
        }

        albumRepository.hasNetworkErrorOccurred = true

        val newStateData: StateData<List<Album>> = loadAlbumsListUseCase.execute(false)
        Assert.assertTrue(newStateData is StateData.Error)
        if (newStateData is StateData.Error) {
            Assert.assertEquals(FakeAlbumRepository.GET_NETWORK_ERROR_MESSAGE, newStateData.errorMessage)
        }
    }
}
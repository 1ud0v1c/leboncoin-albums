package com.ludovic.vimont.domain.usescases

import com.ludovic.vimont.domain.FakeAlbumRepository
import com.ludovic.vimont.domain.common.DataStatus
import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.domain.usecases.LoadAlbumsListUseCase
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
    fun testGetListOfAlbums() {
        val stateData: StateData<List<Album>> = loadAlbumsListUseCase.execute(false)
        Assert.assertEquals(DataStatus.SUCCESS, stateData.status)
        Assert.assertEquals(FakeAlbumRepository.ALBUMS_LIST_SIZE, stateData.data?.size)

        albumRepository.hasNetworkErrorOccurred = true

        val newStateData: StateData<List<Album>> = loadAlbumsListUseCase.execute(false)
        Assert.assertEquals(DataStatus.ERROR, newStateData.status)
        Assert.assertNull(newStateData.data)
    }
}
package com.ludovic.vimont.domain.usescases

import com.ludovic.vimont.domain.FakeAlbumRepository
import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.domain.usecases.LoadAlbumUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LoadAlbumUseCaseTest {
    private val albumRepository = FakeAlbumRepository()
    private lateinit var loadAlbumUseCase: LoadAlbumUseCase

    @Before
    fun setUp() {
        loadAlbumUseCase = LoadAlbumUseCase(albumRepository)
    }

    @Test
    fun testGetAlbum() = runBlocking {
        val result: StateData<Album> = loadAlbumUseCase.execute(1)
        Assert.assertTrue(result is StateData.Success)
        if (result is StateData.Success) {
            Assert.assertEquals(1, result.data.id)
        }

        val newResult: StateData<Album> = loadAlbumUseCase.execute(50)
        Assert.assertTrue(newResult is StateData.Error)
        if (newResult is StateData.Error) {
            Assert.assertEquals(FakeAlbumRepository.GET_ALBUM_ERROR_MESSAGE, newResult.errorMessage)
        }
    }
}
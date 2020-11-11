package com.ludovic.vimont.domain.usescases

import com.ludovic.vimont.domain.FakeAlbumRepository
import com.ludovic.vimont.domain.common.DataStatus
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
        Assert.assertEquals(DataStatus.SUCCESS, result.status)
        Assert.assertEquals(1, result.data?.id)

        val newResult: StateData<Album> = loadAlbumUseCase.execute(50)
        Assert.assertEquals(DataStatus.ERROR, newResult.status)
        Assert.assertNull(newResult.data)
    }
}
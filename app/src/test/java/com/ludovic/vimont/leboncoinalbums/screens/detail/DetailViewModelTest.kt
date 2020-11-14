package com.ludovic.vimont.leboncoinalbums.screens.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.domain.usecases.LoadAlbumUseCase
import com.ludovic.vimont.leboncoinalbums.screens.FakeAlbumRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val fakeAlbumRepositoryImpl = FakeAlbumRepositoryImpl()
    private lateinit var viewModel: DetailViewModel
    private lateinit var observer: Observer<StateData<Album>>

    @Before
    fun setUp() {
        observer = Mockito.mock(Observer::class.java) as Observer<StateData<Album>>
        viewModel = DetailViewModel(LoadAlbumUseCase(fakeAlbumRepositoryImpl))
        viewModel.album.observeForever(observer)
    }

    @Test
    fun testLoadAlbum() = runBlocking {
        val lastItemId: Int = fakeAlbumRepositoryImpl.count()
        viewModel.loadAlbum(lastItemId)
        Mockito.verify(observer).onChanged(StateData.loading())
        Mockito.verify(observer).onChanged(StateData.success(fakeAlbumRepositoryImpl.lastAlbum()))
    }

    @Test
    fun testLoadNotExistingAlbum() = runBlocking {
        val albumId = 1500
        viewModel.loadAlbum(albumId)
        Mockito.verify(observer).onChanged(StateData.loading())
        Mockito.verify(observer).onChanged(StateData.error(fakeAlbumRepositoryImpl.errorMessage()))
    }
}
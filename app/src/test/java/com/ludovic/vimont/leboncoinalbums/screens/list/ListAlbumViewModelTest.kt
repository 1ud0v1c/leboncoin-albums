package com.ludovic.vimont.leboncoinalbums.screens.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.domain.usecases.LoadAlbumsListUseCase
import com.ludovic.vimont.leboncoinalbums.screens.FakeAlbumRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.atLeastOnce
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ListAlbumViewModelTest {
    companion object {
        const val LIST_SIZE = 5_000
    }
    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val fakeAlbumRepositoryImpl = FakeAlbumRepositoryImpl(LIST_SIZE)
    private lateinit var viewModel: ListAlbumViewModel
    private lateinit var observer: Observer<StateData<List<Album>>>

    @Before
    fun setUp() {
        observer = Mockito.mock(Observer::class.java) as Observer<StateData<List<Album>>>
        viewModel = ListAlbumViewModel(LoadAlbumsListUseCase(fakeAlbumRepositoryImpl))
        viewModel.albums.observeForever(observer)
    }

    @Test
    fun testLoadAlbums() = runBlocking {
        viewModel.loadAlbums()
        Mockito.verify(observer).onChanged(StateData.loading())
        Mockito.verify(observer).onChanged(StateData.success(fakeAlbumRepositoryImpl.getAlbums().subList(0, 15)))

        viewModel.loadAlbums()
        Mockito.verify(observer, atLeastOnce()).onChanged(StateData.success(fakeAlbumRepositoryImpl.getAlbums().subList(0, 15)))
    }

    @Test
    fun testIsAlbumsNotLoaded() = runBlocking {
        Assert.assertTrue(viewModel.isAlbumsNotLoaded())
        viewModel.loadAlbums()
        Mockito.verify(observer).onChanged(StateData.loading())
        Mockito.verify(observer).onChanged(StateData.success(fakeAlbumRepositoryImpl.getAlbums().subList(0, 15)))
        Assert.assertFalse(viewModel.isAlbumsNotLoaded())
    }

    @Test
    fun testLoadNextPage() = runBlocking {
        viewModel.loadAlbums()
        Mockito.verify(observer).onChanged(StateData.loading())
        Mockito.verify(observer).onChanged(StateData.success(fakeAlbumRepositoryImpl.getAlbums().subList(0, 15)))

        viewModel.loadNextPageList()
        Mockito.verify(observer, atLeastOnce()).onChanged(StateData.success(fakeAlbumRepositoryImpl.getAlbums().subList(0, 30)))
    }
}
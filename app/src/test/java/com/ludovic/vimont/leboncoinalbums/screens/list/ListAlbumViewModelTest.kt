package com.ludovic.vimont.leboncoinalbums.screens.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.domain.usecases.LoadAlbumsListUseCase
import com.ludovic.vimont.leboncoinalbums.screens.FakeAlbumRepositoryImpl
import com.ludovic.vimont.leboncoinalbums.screens.LifeCycleTestOwner
import com.ludovic.vimont.leboncoinalbums.screens.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.*

class ListAlbumViewModelTest {
    companion object {
        const val LIST_SIZE = 5_000
    }
    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var lifeCycleTestOwner: LifeCycleTestOwner
    private val fakeAlbumRepositoryImpl = FakeAlbumRepositoryImpl(LIST_SIZE)
    private lateinit var viewModel: ListAlbumViewModel
    private lateinit var observer: Observer<StateData<List<Album>>>

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        lifeCycleTestOwner = LifeCycleTestOwner()
        lifeCycleTestOwner.onCreate()
        observer = Observer {}
        viewModel = ListAlbumViewModel(LoadAlbumsListUseCase(fakeAlbumRepositoryImpl), TestCoroutineDispatcher())
        viewModel.albums.observeForever(observer)
    }

    @After
    fun tearDown() {
        lifeCycleTestOwner.onDestroy()
    }

    @Test
    fun testLoadAlbums() = runBlocking {
        viewModel.loadAlbums()
        Assert.assertEquals(StateData.Success(fakeAlbumRepositoryImpl.getAlbums().subList(0, 15)), viewModel.albums.getOrAwaitValue())

        viewModel.loadAlbums()
        Assert.assertEquals(StateData.Success(fakeAlbumRepositoryImpl.getAlbums().subList(0, 15)), viewModel.albums.getOrAwaitValue())
    }

    @Test
    fun testIsAlbumsNotLoaded() = runBlocking {
        Assert.assertTrue(viewModel.isAlbumsNotLoaded())
        viewModel.loadAlbums()
        Assert.assertEquals(StateData.Success(fakeAlbumRepositoryImpl.getAlbums().subList(0, 15)), viewModel.albums.getOrAwaitValue())
        Assert.assertFalse(viewModel.isAlbumsNotLoaded())
    }

    @Test
    fun testLoadNextPage() = runBlocking {
        viewModel.loadAlbums()
        Assert.assertEquals(StateData.Success(fakeAlbumRepositoryImpl.getAlbums().subList(0, 15)), viewModel.albums.getOrAwaitValue())

        viewModel.loadNextPageList()
        Assert.assertEquals(StateData.Success(fakeAlbumRepositoryImpl.getAlbums().subList(0, 30)), viewModel.albums.getOrAwaitValue())
    }
}
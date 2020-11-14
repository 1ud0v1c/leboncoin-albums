package com.ludovic.vimont.leboncoinalbums.screens.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.domain.usecases.LoadAlbumUseCase
import com.ludovic.vimont.leboncoinalbums.screens.FakeAlbumRepositoryImpl
import com.ludovic.vimont.leboncoinalbums.screens.LifeCycleTestOwner
import com.ludovic.vimont.leboncoinalbums.screens.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.*

class DetailViewModelTest {
    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var lifeCycleTestOwner: LifeCycleTestOwner
    private val fakeAlbumRepositoryImpl = FakeAlbumRepositoryImpl()
    private lateinit var viewModel: DetailViewModel
    private lateinit var lastStateData: StateData<Album>
    private lateinit var observer: Observer<StateData<Album>>

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        lifeCycleTestOwner = LifeCycleTestOwner()
        lifeCycleTestOwner.onCreate()
        observer = Observer {}
        viewModel = DetailViewModel(LoadAlbumUseCase(fakeAlbumRepositoryImpl), TestCoroutineDispatcher())
        viewModel.album.observe(lifeCycleTestOwner, observer)
    }

    @After
    fun tearDown() {
        lifeCycleTestOwner.onDestroy()
    }

    @Test
    fun testLoadAlbum(): Unit = runBlocking {
        lifeCycleTestOwner.onResume()
        val lastItemId: Int = fakeAlbumRepositoryImpl.count()
        viewModel.loadAlbum(lastItemId)
        Assert.assertEquals(StateData.success(fakeAlbumRepositoryImpl.lastAlbum()), viewModel.album.getOrAwaitValue())
    }

    @Test
    fun testLoadNotExistingAlbum() = runBlocking {
        lifeCycleTestOwner.onResume()
        val albumId = 1500
        viewModel.loadAlbum(albumId)
        Assert.assertEquals(StateData.error<Album>(fakeAlbumRepositoryImpl.errorMessage()), viewModel.album.getOrAwaitValue())
    }
}
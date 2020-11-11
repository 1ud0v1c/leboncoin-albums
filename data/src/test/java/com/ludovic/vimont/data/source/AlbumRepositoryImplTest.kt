package com.ludovic.vimont.data.source

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.ludovic.vimont.data.FakeAlbumAPI
import com.ludovic.vimont.data.db.AlbumDao
import com.ludovic.vimont.data.db.AlbumDatabase
import com.ludovic.vimont.data.network.AlbumAPI
import com.ludovic.vimont.data.network.RetrofitBuilder
import com.ludovic.vimont.domain.common.DataStatus
import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.domain.repositories.AlbumRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P], manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class AlbumRepositoryImplTest {
    private val context: Context = ApplicationProvider.getApplicationContext()
    private lateinit var albumAPI: AlbumAPI
    private lateinit var albumDao: AlbumDao
    private lateinit var albumRepository: AlbumRepository

    @Before
    fun setUp() {
        albumAPI = RetrofitBuilder.buildAPI(AlbumAPI.BASE_URL, AlbumAPI::class.java)
        albumDao = AlbumDatabase.buildDatabase(context).albumDao()
        albumRepository = AlbumRepositoryImpl(albumAPI, albumDao)
    }

    @Test
    fun testGetAlbums() = runBlocking {
        Assert.assertEquals(0, albumDao.count())
        val stateData: StateData<List<Album>> = albumRepository.getAlbums(true)
        Assert.assertEquals(DataStatus.SUCCESS, stateData.status)
        Assert.assertTrue(stateData.data?.isNotEmpty() ?: false)
        Assert.assertNotEquals(0, albumDao.count())
    }

    @Test
    fun testGetAlbumsWithAPIError() = runBlocking {
        albumAPI = FakeAlbumAPI()
        albumRepository = AlbumRepositoryImpl(albumAPI, albumDao)
        val stateData: StateData<List<Album>> = albumRepository.getAlbums(true)
        Assert.assertEquals(DataStatus.ERROR, stateData.status)
    }

    @Test
    fun testGetAlbumsWithAPIErrorButDataInDatabase() = runBlocking {
        albumRepository.getAlbums(true)
        albumAPI = FakeAlbumAPI()
        albumRepository = AlbumRepositoryImpl(albumAPI, albumDao)

        val stateData: StateData<List<Album>> = albumRepository.getAlbums(false)
        Assert.assertEquals(DataStatus.SUCCESS, stateData.status)
        Assert.assertTrue(stateData.data?.isNotEmpty() ?: false)
        Assert.assertNotEquals(0, albumDao.count())
    }

    @Test
    fun testGetAlbum() = runBlocking {
        val stateData: StateData<Album> = albumRepository.getAlbum(5)
        Assert.assertEquals(DataStatus.ERROR, stateData.status)
        Assert.assertNull(stateData.data)

        albumRepository.getAlbums(true)

        val newStateData: StateData<Album> = albumRepository.getAlbum(5)
        Assert.assertEquals(DataStatus.SUCCESS, newStateData.status)
        Assert.assertNotNull(newStateData.data)
    }
}
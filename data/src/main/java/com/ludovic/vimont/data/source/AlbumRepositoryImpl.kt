package com.ludovic.vimont.data.source

import com.ludovic.vimont.data.db.AlbumDao
import com.ludovic.vimont.data.network.AlbumAPI
import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.domain.repositories.AlbumRepository
import retrofit2.Response

/**
 * Implementation of the class AlbumRepository from the domain module.
 * We use Retrofit to get data from the server and Room to persist the received data.
 */
class AlbumRepositoryImpl(private val albumAPI: AlbumAPI,
                          private val albumDao: AlbumDao): AlbumRepository {
    /**
     * After the first fetch from the server, we use the database as reference.
     */
    override suspend fun getAlbums(isRefreshNeeded: Boolean): StateData<List<Album>> {
        val albums = ArrayList<Album>()
        if (isRefreshNeeded || albumDao.count() <= 0) {
            try {
                val albumsResponse: Response<List<Album>> = albumAPI.getAlbums()
                if (!albumsResponse.isSuccessful) {
                    return StateData.Error(albumsResponse.message())
                }
                albumsResponse.body()?.let { receivedAlbums: List<Album> ->
                    albums.addAll(receivedAlbums)
                    albumDao.insertAll(receivedAlbums.asData())
                }
                return StateData.Success(albums)
            } catch (exception: Exception) {
                return StateData.Error(exception.message.toString())
            }
        } else {
            return try {
                val cachedAlbums: List<Album> = albumDao.getAll().asEntity()
                StateData.Success(cachedAlbums)
            }  catch (exception: Exception) {
                StateData.Error(exception.message.toString())
            }
        }
    }

    override suspend fun getAlbum(albumId: Int): StateData<Album> {
        return try {
            StateData.Success(albumDao.getAlbum(albumId).asEntity())
        }  catch (exception: Exception) {
            StateData.Error(exception.message.toString())
        }
    }
}
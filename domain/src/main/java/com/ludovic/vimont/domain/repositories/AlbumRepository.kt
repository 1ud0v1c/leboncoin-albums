package com.ludovic.vimont.domain.repositories

import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album

/**
 * Business class which offers two uses cases:
 * <ul>
 *     <li>getAlbums: get a list of albums from any data source (like internet or database)</li>
 *     <li>getAlbum: return one album from the previous list given the ID of the album</li>
 * </ul>
 */
interface AlbumRepository {
    /**
     * @param isRefreshNeeded useful if you want to be able to force a refresh from internet over the database
     * for example to be able to refresh the data after a certain amount of time or if the user want to make
     * a Pull to refresh action.
     */
    suspend fun getAlbums(isRefreshNeeded: Boolean = false): StateData<List<Album>>

    suspend fun getAlbum(albumId: Int): StateData<Album>
}
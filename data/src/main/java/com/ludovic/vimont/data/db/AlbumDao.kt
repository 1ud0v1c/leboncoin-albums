package com.ludovic.vimont.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ludovic.vimont.data.model.Album

@Dao
interface AlbumDao {
    @Query("SELECT count(id) FROM album")
    suspend fun count(): Int

    @Query("SELECT * FROM album")
    suspend fun getAll(): List<Album>

    @Query(value = "SELECT * FROM album WHERE id = :id")
    suspend fun getAlbum(id: Int): Album

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<Album>)
}
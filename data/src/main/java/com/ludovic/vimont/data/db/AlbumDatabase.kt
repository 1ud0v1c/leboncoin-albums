package com.ludovic.vimont.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ludovic.vimont.data.model.Album

@Database(entities = [Album::class], version = 1)
abstract class AlbumDatabase: RoomDatabase() {
    companion object {
        private const val DATABASE_NAME = "leboncoin_albums_database"

        fun buildDatabase(context: Context): AlbumDatabase {
            return Room.databaseBuilder(context, AlbumDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun albumDao(): AlbumDao
}
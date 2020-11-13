package com.ludovic.vimont.leboncoinalbums.di

import com.ludovic.vimont.data.db.AlbumDatabase
import com.ludovic.vimont.data.network.AlbumAPI
import com.ludovic.vimont.data.network.RetrofitBuilder
import com.ludovic.vimont.data.source.AlbumRepositoryImpl
import com.ludovic.vimont.domain.usecases.LoadAlbumUseCase
import com.ludovic.vimont.domain.usecases.LoadAlbumsListUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

object DataModule {
    val dataModule: Module = module {
        single {
            RetrofitBuilder.buildAPI(AlbumAPI.BASE_URL, AlbumAPI::class.java)
        }
        single {
            AlbumDatabase.buildDatabase(androidContext()).albumDao()
        }
        single {
            AlbumRepositoryImpl(get(), get())
        }
        single {
            LoadAlbumsListUseCase(get<AlbumRepositoryImpl>())
        }
        single {
            LoadAlbumUseCase(get<AlbumRepositoryImpl>())
        }
    }
}
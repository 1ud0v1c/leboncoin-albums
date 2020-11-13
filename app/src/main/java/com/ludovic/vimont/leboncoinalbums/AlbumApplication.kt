package com.ludovic.vimont.leboncoinalbums

import android.app.Application
import com.ludovic.vimont.data.db.AlbumDatabase
import com.ludovic.vimont.data.network.AlbumAPI
import com.ludovic.vimont.data.network.RetrofitBuilder
import com.ludovic.vimont.data.source.AlbumRepositoryImpl
import com.ludovic.vimont.domain.usecases.LoadAlbumUseCase
import com.ludovic.vimont.domain.usecases.LoadAlbumsListUseCase
import com.ludovic.vimont.leboncoinalbums.di.DataModule
import com.ludovic.vimont.leboncoinalbums.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class AlbumApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AlbumApplication)
            val listOfModule: List<Module> = listOf(
                DataModule.dataModule,
                ViewModelModule.viewModelModule
            )
            modules(listOfModule)
        }
    }
}
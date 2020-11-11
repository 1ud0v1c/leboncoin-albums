package com.ludovic.vimont.leboncoinalbums

import android.app.Application
import com.ludovic.vimont.data.db.AlbumDatabase
import com.ludovic.vimont.data.network.AlbumAPI
import com.ludovic.vimont.data.network.RetrofitBuilder
import com.ludovic.vimont.data.source.AlbumRepositoryImpl
import com.ludovic.vimont.domain.usecases.LoadAlbumsListUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class AlbumApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val dataModule: Module = buildDataModule()

        startKoin {
            androidContext(this@AlbumApplication)
            val listOfModule: List<Module> = listOf(dataModule)
            modules(listOfModule)
        }
    }

    private fun buildDataModule(): Module {
        return module {
            single {
                RetrofitBuilder.buildAPI(AlbumAPI.BASE_URL, AlbumAPI::class.java)
            }
            single {
                AlbumDatabase.buildDatabase(androidContext()).albumDao()
            }
            single {
                LoadAlbumsListUseCase(AlbumRepositoryImpl(get(), get()))
            }
        }
    }
}
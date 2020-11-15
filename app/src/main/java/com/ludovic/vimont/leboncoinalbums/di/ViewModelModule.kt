package com.ludovic.vimont.leboncoinalbums.di

import androidx.lifecycle.SavedStateHandle
import com.ludovic.vimont.leboncoinalbums.screens.detail.DetailViewModel
import com.ludovic.vimont.leboncoinalbums.screens.list.ListAlbumViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object ViewModelModule {
    val viewModelModule: Module = module {
        viewModel {
            (savedStateHandle: SavedStateHandle) -> ListAlbumViewModel(get(), savedStateHandle)
        }
        viewModel {
            (savedStateHandle: SavedStateHandle) -> DetailViewModel(get(), savedStateHandle)
        }
    }
}
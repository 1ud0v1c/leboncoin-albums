package com.ludovic.vimont.leboncoinalbums.screens.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.domain.usecases.LoadAlbumsListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class ListAlbumViewModel: ViewModel(), KoinComponent {
    private val loadAlbumsListUseCase: LoadAlbumsListUseCase by inject()
    val albums = MutableLiveData<StateData<List<Album>>>()

    fun loadAlbums() {
        viewModelScope.launch(Dispatchers.Default) {
            albums.postValue(StateData.loading())
            val result: StateData<List<Album>> = loadAlbumsListUseCase.execute(false)
            albums.postValue(result)
        }
    }
}
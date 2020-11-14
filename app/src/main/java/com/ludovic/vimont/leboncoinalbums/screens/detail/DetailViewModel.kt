package com.ludovic.vimont.leboncoinalbums.screens.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.domain.usecases.LoadAlbumUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val loadAlbumUseCase: LoadAlbumUseCase,
                      private val dispatcher: CoroutineDispatcher = Dispatchers.Default): ViewModel() {
    val album = MutableLiveData<StateData<Album>>()

    fun loadAlbum(albumId: Int) {
        viewModelScope.launch(dispatcher) {
            album.postValue(StateData.Loading)
            val result: StateData<Album> = loadAlbumUseCase.execute(albumId)
            album.postValue(result)
        }
    }
}
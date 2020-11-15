package com.ludovic.vimont.leboncoinalbums.screens.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.domain.usecases.LoadAlbumUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val loadAlbumUseCase: LoadAlbumUseCase,
                      private val savedStateHandle: SavedStateHandle,
                      private val dispatcher: CoroutineDispatcher = Dispatchers.Default): ViewModel() {
    companion object {
        const val KEY_LAST_ALBUM_ID = "KEY_LAST_ALBUM_ID"
    }
    private var lastAlbumId: Int = savedStateHandle[KEY_LAST_ALBUM_ID] ?: -1
    val album = MutableLiveData<StateData<Album>>()

    fun loadAlbum(albumId: Int) {
        viewModelScope.launch(dispatcher) {
            album.postValue(StateData.Loading)
            val result: StateData<Album> = loadAlbumUseCase.execute(albumId)
            album.postValue(result)
            lastAlbumId = albumId
            savedStateHandle[KEY_LAST_ALBUM_ID] = lastAlbumId
        }
    }

    fun restoreLastAlbum() {
        viewModelScope.launch(dispatcher) {
            if (lastAlbumId != -1) {
                album.postValue(StateData.Loading)
                val result: StateData<Album> = loadAlbumUseCase.execute(lastAlbumId)
                album.postValue(result)
            } else {
                album.postValue(StateData.Error("Could not restore the last album..."))
            }
        }
    }
}
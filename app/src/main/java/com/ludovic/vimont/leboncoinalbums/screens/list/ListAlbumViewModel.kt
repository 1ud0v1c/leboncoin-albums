package com.ludovic.vimont.leboncoinalbums.screens.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ludovic.vimont.domain.common.DataStatus
import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.domain.usecases.LoadAlbumsListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListAlbumViewModel(private val loadAlbumsListUseCase: LoadAlbumsListUseCase): ViewModel() {
    private val allAlbums = ArrayList<Album>()
    val albums = MutableLiveData<StateData<List<Album>>>()

    fun loadAlbums() {
        viewModelScope.launch(Dispatchers.Default) {
            if (allAlbums.isEmpty()) {
                albums.postValue(StateData.loading())
                val result: StateData<List<Album>> = loadAlbumsListUseCase.execute(false)
                if (result.status == DataStatus.SUCCESS) {
                    result.data?.let {
                        allAlbums.addAll(it)
                    }
                }
                albums.postValue(result)
            }
        }
    }
}
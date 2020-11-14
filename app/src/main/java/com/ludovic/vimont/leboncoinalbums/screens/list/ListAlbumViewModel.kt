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
    private var fromIndex: Int = 0
    private val currentAlbums = ArrayList<Album>()
    private val allAlbums = ArrayList<Album>()
    val albums = MutableLiveData<StateData<List<Album>>>()

    init {
        println("allAlbums size: ${allAlbums.size}")
        println("currentAlbums size: ${currentAlbums.size}")
    }

    fun loadAlbums() {
        viewModelScope.launch(Dispatchers.Default) {
            println("allAlbums.isEmpty(): ${allAlbums.isEmpty()}")
            if (allAlbums.isEmpty()) {
                albums.postValue(StateData.loading())
                val result: StateData<List<Album>> = loadAlbumsListUseCase.execute(false)
                println("result.status: ${result.status}")
                when (result.status) {
                    DataStatus.SUCCESS -> {
                        result.data?.let {
                            allAlbums.addAll(it)
                            currentAlbums.addAll(allAlbums.subList(fromIndex, ListAlbumFragment.NUMBER_OF_ITEMS_PER_PAGE))
                            albums.postValue(StateData.success(currentAlbums))
                            fromIndex = ListAlbumFragment.NUMBER_OF_ITEMS_PER_PAGE
                        }
                    }
                    DataStatus.ERROR -> {
                        albums.postValue(result)
                    }
                    else -> { }
                }
            } else {
                albums.postValue(StateData.success(currentAlbums))
            }
        }
    }

    fun isAlbumsNotLoaded(): Boolean {
        return allAlbums.isEmpty()
    }

    fun loadNextPageList() {
        println("fromIndex: $fromIndex")
        println("allAlbums.size: ${allAlbums.size}")
        println("fromIndex < allAlbums.size.size: ${fromIndex < allAlbums.size}")
        if (fromIndex < allAlbums.size) {
            val newIndex: Int = fromIndex + ListAlbumFragment.NUMBER_OF_ITEMS_PER_PAGE
            currentAlbums.addAll(allAlbums.subList(fromIndex, newIndex))
            albums.postValue(StateData.success(currentAlbums))
            fromIndex = newIndex
            println("currentAlbums.size: ${currentAlbums.size}")
        }
    }
}
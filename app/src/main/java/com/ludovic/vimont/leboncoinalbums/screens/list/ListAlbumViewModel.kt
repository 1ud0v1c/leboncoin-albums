package com.ludovic.vimont.leboncoinalbums.screens.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.domain.usecases.LoadAlbumsListUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListAlbumViewModel(private val loadAlbumsListUseCase: LoadAlbumsListUseCase,
                         private val dispatcher: CoroutineDispatcher = Dispatchers.Default): ViewModel() {
    private var fromIndex: Int = 0
    private val currentAlbums = ArrayList<Album>()
    private val allAlbums = ArrayList<Album>()
    val albums = MutableLiveData<StateData<List<Album>>>()

    /**
     * After receiving the data from the server, we keep them in memory and provide a progressive load
     * using a sublist.
     */
    fun loadAlbums() {
        viewModelScope.launch(dispatcher) {
            if (allAlbums.isEmpty()) {
                albums.postValue(StateData.Loading)
                when (val result: StateData<List<Album>> = loadAlbumsListUseCase.execute(false)) {
                    is StateData.Success -> {
                        result.data.let {
                            allAlbums.addAll(it)
                            currentAlbums.addAll(allAlbums.subList(fromIndex, ListAlbumFragment.NUMBER_OF_ITEMS_PER_PAGE))
                            albums.postValue(StateData.Success(currentAlbums))
                            fromIndex = ListAlbumFragment.NUMBER_OF_ITEMS_PER_PAGE
                        }
                    }
                    is StateData.Error -> {
                        albums.postValue(result)
                    }
                }
            } else {
                albums.postValue(StateData.Success(currentAlbums))
            }
        }
    }

    fun isAlbumsNotLoaded(): Boolean {
        return allAlbums.isEmpty()
    }

    /**
     * We load, the next batch of data based on the user progression while scrolling the RecyclerView
     */
    fun loadNextPageList() {
        if (fromIndex < allAlbums.size) {
            val newIndex: Int = fromIndex + ListAlbumFragment.NUMBER_OF_ITEMS_PER_PAGE
            currentAlbums.addAll(allAlbums.subList(fromIndex, newIndex))
            albums.postValue(StateData.Success(currentAlbums))
            fromIndex = newIndex
        }
    }
}
package com.ludovic.vimont.leboncoinalbums.screens.detail

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.viewModels
import coil.load
import com.ludovic.vimont.domain.common.DataStatus
import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.leboncoinalbums.R
import com.ludovic.vimont.leboncoinalbums.databinding.FragmentDetailAlbumBinding

class DetailFragment: Fragment() {
    companion object {
        private const val KEY_ALBUM_ID = "album_id"

        fun newInstance(activity: Activity, albumId: Int): Fragment {
            return FragmentFactory.loadFragmentClass(activity.classLoader, DetailFragment::class.java.name)
                .newInstance()
                .apply {
                    arguments = Bundle().apply {
                        putInt(KEY_ALBUM_ID, albumId)
                    }
                }
        }
    }
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: FragmentDetailAlbumBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            val selectedAlbumId: Int = arguments?.getInt(KEY_ALBUM_ID) ?: -1
            viewModel.loadAlbum(selectedAlbumId)
        }

        viewModel.album.observe(viewLifecycleOwner, { result: StateData<Album> ->
            when (result.status) {
                DataStatus.LOADING -> {
                    println("Loading album...")
                }
                DataStatus.SUCCESS -> {
                    result.data?.let { album: Album ->
                        with(binding) {
                            textViewAlbumTitle.text = album.id.toString()
                            imageViewAlbumPhoto.load(album.url) {
                                placeholder(R.drawable.album_default_cover)
                                crossfade(true)
                            }
                        }
                    }
                }
                DataStatus.ERROR -> {
                    println(result.errorMessage)
                }
            }
        })
    }
}
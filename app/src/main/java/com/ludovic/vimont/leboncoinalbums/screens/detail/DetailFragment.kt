package com.ludovic.vimont.leboncoinalbums.screens.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.ludovic.vimont.domain.common.DataStatus
import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.leboncoinalbums.R
import com.ludovic.vimont.leboncoinalbums.databinding.FragmentDetailAlbumBinding
import java.util.*

class DetailFragment: Fragment() {
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: FragmentDetailAlbumBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.loadAlbum(args.albumId)
            setViewModelObserver()
        }
    }

    private fun setViewModelObserver() {
        viewModel.album.observe(viewLifecycleOwner, { result: StateData<Album> ->
            if (result.status == DataStatus.SUCCESS) {
                result.data?.let { album: Album ->
                    configureViews(album)
                }
            }
        })
    }

    private fun configureViews(album: Album) {
        with(binding) {
            textViewAlbumId.text = album.id.toString()
            textViewAlbumTitle.text = album.title.capitalize(Locale.getDefault()).trim()
            imageViewAlbumPhoto.load(album.url) {
                placeholder(R.drawable.album_default_cover)
                crossfade(true)
            }
        }
    }
}
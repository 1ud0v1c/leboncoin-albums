package com.ludovic.vimont.leboncoinalbums.screens.list

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ludovic.vimont.domain.common.DataStatus
import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.leboncoinalbums.databinding.FragmentListAlbumsBinding

class ListAlbumFragment: Fragment() {
    companion object {
        fun newInstance(activity: Activity): Fragment {
            return FragmentFactory.loadFragmentClass(activity.classLoader, ListAlbumFragment::class.java.name)
                .newInstance()
        }
    }
    private val adapter = ListAlbumAdapter(ArrayList())
    private val viewModel: ListAlbumViewModel by viewModels()
    private lateinit var binding: FragmentListAlbumsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListAlbumsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.layoutManager = GridLayoutManager(context, 2)
            recyclerView.adapter = adapter
        }

        if (savedInstanceState == null) {
            viewModel.loadAlbums()
        }
        viewModel.albums.observe(viewLifecycleOwner, { result: StateData<List<Album>> ->
            when (result.status) {
                DataStatus.SUCCESS -> {
                    result.data?.let { adapter.addItems(it) }
                }
                DataStatus.ERROR -> {
                    println(result.errorMessage)
                }
            }
        })
    }
}
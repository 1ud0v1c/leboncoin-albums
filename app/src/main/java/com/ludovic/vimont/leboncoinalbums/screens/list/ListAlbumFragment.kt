package com.ludovic.vimont.leboncoinalbums.screens.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ludovic.vimont.domain.common.DataStatus
import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.leboncoinalbums.R
import com.ludovic.vimont.leboncoinalbums.databinding.FragmentListAlbumsBinding
import com.ludovic.vimont.leboncoinalbums.helper.ViewHelper
import com.ludovic.vimont.leboncoinalbums.ui.EndlessRecyclerViewScrollListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListAlbumFragment: Fragment() {
    companion object {
        const val NUMBER_OF_ITEMS_PER_PAGE = 15
    }
    private val adapter = ListAlbumAdapter(ArrayList())
    private val viewModel: ListAlbumViewModel by viewModel()
    private lateinit var binding: FragmentListAlbumsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListAlbumsBinding.inflate(inflater, container, false)
        activity?.title = getString(R.string.fragment_list_title)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        if (savedInstanceState == null && viewModel.isAlbumsNotLoaded()) {
            viewModel.loadAlbums()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setViewModelObserver()
    }

    private fun configureViews() {
        with(binding) {
            val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            recyclerViewAlbums.layoutManager = layoutManager
            recyclerViewAlbums.adapter = adapter
            val endlessRecyclerViewScrollListener = object: EndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(currentPage: Int) {
                    viewModel.loadNextPageList()
                }
            }
            recyclerViewAlbums.addOnScrollListener(endlessRecyclerViewScrollListener)
            adapter.onItemClick = { albumId: Int ->
                activity?.let {
                    val action: NavDirections = ListAlbumFragmentDirections.actionListAlbumFragmentToDetailFragment(albumId)
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun setViewModelObserver() {
        viewModel.albums.observe(viewLifecycleOwner) { result: StateData<List<Album>> ->
            when (result.status) {
                DataStatus.LOADING -> {
                    showLoadingStatus()
                }
                DataStatus.SUCCESS -> {
                    result.data?.let {
                        showSuccessStatus(it)
                    }
                }
                DataStatus.ERROR -> {
                    showErrorStatus(result.errorMessage)
                }
            }
        }
    }

    private fun showLoadingStatus() {
        with(binding) {
            ViewHelper.fadeOutAnimation(recyclerViewAlbums, {
                recyclerViewAlbums.visibility = View.GONE
            })
            ViewHelper.fadeInAnimation(linearLayoutStateContainer, {
                linearLayoutStateContainer.visibility = View.VISIBLE
            })
            imageViewState.setImageResource(R.drawable.state_loading)
            textViewStateTitle.text = getString(R.string.fragment_list_loading_title)
            textViewStateDescription.text = getString(R.string.fragment_list_loading_description)
            buttonStateAction.visibility = View.GONE
        }
    }

    private fun showSuccessStatus(albums: List<Album>) {
        with(binding) {
            if (linearLayoutStateContainer.isVisible) {
                ViewHelper.fadeOutAnimation(linearLayoutStateContainer, {
                    linearLayoutStateContainer.visibility = View.GONE
                })
                ViewHelper.fadeInAnimation(recyclerViewAlbums, {
                    recyclerViewAlbums.visibility = View.VISIBLE
                })
            }
            adapter.setItems(albums)
        }
    }

    private fun showErrorStatus(errorMessage: String) {
        with(binding) {
            ViewHelper.fadeOutAnimation(recyclerViewAlbums, {
                recyclerViewAlbums.visibility = View.GONE
            })
            ViewHelper.fadeInAnimation(linearLayoutStateContainer, {
                linearLayoutStateContainer.visibility = View.VISIBLE
            })
            imageViewState.setImageResource(R.drawable.state_error)
            textViewStateTitle.text = getString(R.string.fragment_list_error_title)
            textViewStateDescription.text = errorMessage

            buttonStateAction.text = getString(R.string.action_retry)
            buttonStateAction.visibility = View.VISIBLE
            buttonStateAction.setOnClickListener {
                viewModel.loadAlbums()
            }
        }
    }
}
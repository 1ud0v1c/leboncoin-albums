package com.ludovic.vimont.leboncoinalbums.screens.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ludovic.vimont.domain.common.StateData
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.leboncoinalbums.R
import com.ludovic.vimont.leboncoinalbums.databinding.FragmentListAlbumsBinding
import com.ludovic.vimont.leboncoinalbums.helper.ViewHelper
import com.ludovic.vimont.leboncoinalbums.ui.EndlessRecyclerViewScrollListener
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class ListAlbumFragment: Fragment() {
    companion object {
        const val NUMBER_OF_ITEMS_PER_PAGE = 15
    }
    private val adapter = ListAlbumAdapter(ArrayList())
    private val viewModel: ListAlbumViewModel by stateViewModel()
    private var lastStateData: StateData<List<Album>>? = null
    private lateinit var binding: FragmentListAlbumsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListAlbumsBinding.inflate(inflater, container, false)
        activity?.title = getString(R.string.fragment_list_title)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        if (savedInstanceState == null || viewModel.isAlbumsNotLoaded()) {
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
            val endlessRecyclerViewScrollListener = object: EndlessRecyclerViewScrollListener(
                layoutManager
            ) {
                override fun onLoadMore(currentPage: Int) {
                    viewModel.loadNextPageList()
                }
            }
            recyclerViewAlbums.addOnScrollListener(endlessRecyclerViewScrollListener)
            adapter.onItemClick = { albumId: Int ->
                activity?.let {
                    val action: NavDirections = ListAlbumFragmentDirections.actionListAlbumFragmentToDetailFragment(
                        albumId
                    )
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun setViewModelObserver() {
        viewModel.albums.observe(viewLifecycleOwner) { result: StateData<List<Album>> ->
            when (result) {
                is StateData.Loading -> showLoadingStatus()
                is StateData.Success -> showSuccessStatus(result.data)
                is StateData.Error -> showErrorStatus(result.errorMessage)
            }
            lastStateData = result
        }
    }

    private fun showLoadingStatus() {
        with(binding) {
            if (lastStateData == null || lastStateData !is StateData.Loading) {
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
    }

    private fun showSuccessStatus(albums: List<Album>) {
        with(binding) {
            if (lastStateData == null || lastStateData !is StateData.Success) {
                ViewHelper.fadeOutAnimation(linearLayoutStateContainer, {
                    linearLayoutStateContainer.visibility = View.GONE
                })
                ViewHelper.fadeInAnimation(recyclerViewAlbums, {
                    recyclerViewAlbums.visibility = View.VISIBLE
                })
            }
            adapter.setItems(albums)
            val lastScrollPosition: Int = viewModel.getLastScrollPosition()
            if (lastScrollPosition != -1) {
                recyclerViewAlbums.layoutManager?.scrollToPosition(lastScrollPosition)
            }
        }
    }

    private fun showErrorStatus(errorMessage: String) {
        with(binding) {
            if (lastStateData == null || lastStateData !is StateData.Error) {
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

    override fun onPause() {
        super.onPause()
        val layoutManager: RecyclerView.LayoutManager? = binding.recyclerViewAlbums.layoutManager
        if (layoutManager is LinearLayoutManager) {
            viewModel.saveCurrentScrollPosition(layoutManager.findFirstCompletelyVisibleItemPosition())
        }
    }
}
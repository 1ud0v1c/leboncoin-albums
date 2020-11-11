package com.ludovic.vimont.leboncoinalbums.screens.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.ludovic.vimont.domain.entities.Album
import com.ludovic.vimont.leboncoinalbums.R

class ListAlbumAdapter(private val albums: ArrayList<Album>): RecyclerView.Adapter<ListAlbumAdapter.AlbumViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album: Album = albums[position]
        holder.textViewTitle.text = album.title
        holder.imageViewPhoto.load(album.thumbnailUrl) {
            placeholder(R.drawable.album_default_cover)
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    fun addItems(items: List<Album>) {
        val lastAlbumsSize: Int = albums.size
        albums.addAll(items)
        val newAlbumsSize: Int = albums.size
        if (newAlbumsSize > lastAlbumsSize) {
            notifyItemRangeChanged(lastAlbumsSize, newAlbumsSize)
        }
    }

    inner class AlbumViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageViewPhoto: ImageView = itemView.findViewById(R.id.image_view_album_thumbnail)
        val textViewTitle: TextView = itemView.findViewById(R.id.text_view_album_title)
    }
}
package com.example.movieapp.ui.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movieapp.data.response.MovieApiResponseItem
import com.example.movieapp.databinding.ItemMovieBinding

class MovieListAdapter : ListAdapter<MovieApiResponseItem, MovieListAdapter.MovieItemViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        return MovieItemViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MovieItemViewHolder(private var binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var currentMovie: MovieApiResponseItem? = null

        init {

        }

        fun bind(movie: MovieApiResponseItem?) {
            currentMovie = movie
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<MovieApiResponseItem>() {
            override fun areItemsTheSame(oldItem: MovieApiResponseItem, newItem: MovieApiResponseItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieApiResponseItem, newItem: MovieApiResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}
package com.example.movieapp.ui.movielist

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.MainViewModel
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private var canLoadMyFeed: Boolean = true
    val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentMovieListBinding

    @Inject
    lateinit var movieAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieListBinding.bind(view)
        init()
        viewModel.getMovieListData()
        setUpLoadMoreListener()
        setObservers()
    }

    private fun setUpLoadMoreListener() {
        binding.movieRv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    //check for scroll down
                    val visibleItemCount = Objects.requireNonNull(recyclerView.layoutManager)!!.childCount
                    val totalItemCount = recyclerView.layoutManager!!.itemCount
                    val pastVisiblesItems =  (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
                    if (canLoadMyFeed) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            canLoadMyFeed = false
                            viewModel.getMovieListData()
                        }
                    }
                }
            }
        } )
    }

    @SuppressLint("LogNotTimber")
    private fun setObservers() {
        viewModel.movieListData.observe(viewLifecycleOwner) { list ->
            list?.let {
                canLoadMyFeed = true
                movieAdapter.submitList(it)
            }
        }
    }

    private fun init() {
        movieAdapter.onMovieItemClicked { movie ->
            viewModel.selectedMovie = movie
            findNavController().navigate(MovieListFragmentDirections.actionNavigateToDetailScreen())
        }
        binding.movieRv.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }
    }
}
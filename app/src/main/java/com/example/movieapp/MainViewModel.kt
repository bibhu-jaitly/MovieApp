package com.example.movieapp

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.response.MovieApiResponseItem
import com.example.movieapp.data.response.RatingListResponseItem
import com.example.movieapp.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private var _movieListData: MutableLiveData<List<MovieApiResponseItem>> = MutableLiveData()
    val movieListData: LiveData<List<MovieApiResponseItem>> = _movieListData

    var selectedMovie: MovieApiResponseItem? = null
    var pageNumber = 1

    init {
        getRatingData()
    }

    @SuppressLint("LogNotTimber")
    fun getMovieListData() {
        viewModelScope.launch {
            val response = networkRepository.getMovieList(pageNumber)
            if (response.isSuccessful) {
                if (response.body()?.size != 0) {
                    pageNumber++
                }
                Log.d("innerChk****", "enter ${response.body()?.size}  page $pageNumber")
                val list = mutableListOf<MovieApiResponseItem>()
                _movieListData.value?.toMutableList()?.let { list.addAll(it) }
                response.body()?.toMutableList()?.let { list.addAll(it) }
                _movieListData.postValue(list)
            } else {
                Log.d("errorResponse***", "${response.errorBody()}")
            }
        }
    }

    fun getRatingData() {
        viewModelScope.launch {
            while (true) {
                delay(15000)
                val response = networkRepository.getMovieRating()
                withContext(Dispatchers.Default) {
                    if (response.isSuccessful) {
                        val ratingList = mutableListOf<RatingListResponseItem>()
                        response.body()?.toMutableList()?.let { ratingList.addAll(it) }
                        val list = _movieListData.value
                        ratingList?.forEach { ratingItem ->
                            val movieApiResponseItem = list?.find { it.id == ratingItem.id }
                            movieApiResponseItem?.let {
                                Log.d("ratingTest***", "old : ${it.rating} new : ${ratingItem.rating}")
                                it.rating = ratingItem.rating
                            }
                            /*list?.forEach { movieItem ->
                                if (ratingItem.id == movieItem.id) {
                                    movieItem.rating = ratingItem.rating
                                }
                            }*/
                        }
                        withContext(Dispatchers.Main) {
                            list?.let {
                                Log.d("update*****", "listUpdate")
                                _movieListData.postValue(it)
                            }
                        }
                    } else {
                        Log.d("ratingError****", "${response.errorBody()}")
                    }
                }
            }
        }
    }

}
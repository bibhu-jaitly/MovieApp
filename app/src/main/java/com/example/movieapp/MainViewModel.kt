package com.example.movieapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.response.MovieApiResponseItem
import com.example.movieapp.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private var _movieListData: MutableLiveData<List<MovieApiResponseItem>> = MutableLiveData()
    val movieListData: LiveData<List<MovieApiResponseItem>> = _movieListData



    fun getMovieListData() {
        viewModelScope.launch {
            val response = networkRepository.getMovieList(1)
            if (response.isSuccessful) {
                _movieListData.postValue(response.body())
            } else {
                Log.d("errorResponse***", "${response.errorBody()}")
            }
        }
    }

}
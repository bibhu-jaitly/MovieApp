package com.example.movieapp.repository

import com.example.movieapp.apiservice.MovieApiService
import com.example.movieapp.data.response.MovieApiResponse
import com.example.movieapp.data.response.MovieApiResponseItem
import com.example.movieapp.data.response.RatingListResponse
import com.example.movieapp.data.response.RatingListResponseItem
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val movieApiService: MovieApiService) {
    suspend fun getMovieList(page: Int): Response<List<MovieApiResponseItem>> {
        return movieApiService.getMovieData(page)
    }

    suspend fun getMovieRating(): Response<List<RatingListResponseItem>> {
        return movieApiService.getMovieRating()
    }
}
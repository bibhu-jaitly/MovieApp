package com.example.movieapp.data.response

data class MovieApiResponseItem(
    var actors: String,
    var director: String,
    var genres: List<String>,
    var id: Int,
    var plot: String,
    var posterUrl: String,
    var  rating: Double,
    val runtime: Int,
    var title: String,
    var year: Int
)
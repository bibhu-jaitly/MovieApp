package com.example.movieapp.data.response

data class MovieApiResponseItem(
    val actors: String,
    val director: String,
    val genres: List<String>,
    val id: Int,
    val plot: String,
    val posterUrl: String,
    val rating: Double,
    val runtime: Int,
    val title: String,
    val year: Int
)
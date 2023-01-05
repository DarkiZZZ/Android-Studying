package ru.msokolov.movieaggregator.retrofit.entities

import com.google.gson.annotations.SerializedName

data class ApiMovieResponse(
    @SerializedName("docs")
    var movies: List<Movie>
)
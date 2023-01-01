package ru.msokolov.movieaggregator

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("docs")
    var movies: List<Movie>
)
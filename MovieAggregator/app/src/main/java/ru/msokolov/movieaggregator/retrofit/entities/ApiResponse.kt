package ru.msokolov.movieaggregator.retrofit.entities

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("docs")
    var movies: List<Movie>
)
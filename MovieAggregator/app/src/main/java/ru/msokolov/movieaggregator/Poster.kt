package ru.msokolov.movieaggregator

import com.google.gson.annotations.SerializedName

data class Poster(
    @SerializedName("url")
    var url: String
)

package ru.msokolov.movieaggregator

import com.google.gson.annotations.SerializedName

data class Poster(
    @SerializedName("poster")
    var url: String
)

package ru.msokolov.movieaggregator.retrofit.entities

import com.google.gson.annotations.SerializedName

data class Poster(
    @SerializedName("url")
    var url: String
)

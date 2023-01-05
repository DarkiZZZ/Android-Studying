package ru.msokolov.movieaggregator.retrofit.entities

import com.google.gson.annotations.SerializedName

data class Trailer(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    var url: String
)

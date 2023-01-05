package ru.msokolov.movieaggregator.retrofit.entities

import com.google.gson.annotations.SerializedName

data class TrailersList(
    @SerializedName("trailers")
    var trailers: List<Trailer>
)

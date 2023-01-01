package ru.msokolov.movieaggregator

import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("kp")
    var kp: String
)

package ru.msokolov.movieaggregator.retrofit.entities

import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("kp")
    var kp: Double
)

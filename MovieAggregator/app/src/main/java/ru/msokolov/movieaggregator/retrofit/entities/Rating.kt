package ru.msokolov.movieaggregator.retrofit.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Rating(
    @SerializedName("kp")
    var kp: Double
) : Serializable

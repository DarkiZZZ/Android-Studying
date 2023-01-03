package ru.msokolov.movieaggregator.retrofit.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Poster(
    @SerializedName("url")
    var url: String
) : Serializable

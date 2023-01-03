package ru.msokolov.movieaggregator.retrofit.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("year")
    var year: Int,

    @SerializedName("poster")
    var poster: Poster,

    @SerializedName("rating")
    var rating: Rating
) : Serializable

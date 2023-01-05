package ru.msokolov.movieaggregator.retrofit.entities

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("author")
    val author: String,
    @SerializedName("review")
    val text: String,
    @SerializedName("type")
    val type: String
)

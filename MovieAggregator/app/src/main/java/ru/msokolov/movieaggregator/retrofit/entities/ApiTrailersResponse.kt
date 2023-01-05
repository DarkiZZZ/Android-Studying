package ru.msokolov.movieaggregator.retrofit.entities

import com.google.gson.annotations.SerializedName

data class ApiTrailersResponse(
    @SerializedName("videos")
    var trailersList: TrailersList
)
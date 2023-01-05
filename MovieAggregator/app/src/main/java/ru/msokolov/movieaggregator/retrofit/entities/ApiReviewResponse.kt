package ru.msokolov.movieaggregator.retrofit.entities

import com.google.gson.annotations.SerializedName

data class ApiReviewResponse(
    @SerializedName("docs")
    var reviewList: List<Review>
)
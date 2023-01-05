package ru.msokolov.movieaggregator.retrofit.entities

import com.google.gson.annotations.SerializedName
import ru.msokolov.movieaggregator.room.entities.RatingEntity
import java.io.Serializable

data class Rating(
    @SerializedName("kp")
    var kp: Double
) : Serializable {

    fun toRatingEntity(): RatingEntity {
        return RatingEntity(
            kp = this.kp
        )
    }
}

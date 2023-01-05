package ru.msokolov.movieaggregator.retrofit.entities

import com.google.gson.annotations.SerializedName
import ru.msokolov.movieaggregator.room.entities.PosterEntity
import java.io.Serializable

data class Poster(
    @SerializedName("url")
    var url: String
) : Serializable {

    fun toPosterEntity(): PosterEntity {
        return PosterEntity(
            url = this.url
        )
    }
}

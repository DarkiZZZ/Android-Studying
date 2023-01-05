package ru.msokolov.movieaggregator.room.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.msokolov.movieaggregator.Constants
import java.io.Serializable

@Entity(tableName = Constants.MOVIE_TABLE)
data class MovieEntity(
    @PrimaryKey
    val id: Int,

    var name: String,

    var description: String,

    var year: Int,

    @Embedded
    var poster: PosterEntity,

    @Embedded
    var rating: RatingEntity
) : Serializable

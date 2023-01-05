package ru.msokolov.movieaggregator.room

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Completable
import ru.msokolov.movieaggregator.room.entities.MovieEntity

interface MovieRepository {

    fun insertMovieToFavourites(movieEntity: MovieEntity): Completable

    fun deleteMovieFromFavourites(movieId: Int): Completable

    fun getAllMoviesFromFavourites(): LiveData<List<MovieEntity>>

    fun getMovieFromFavourites(movieId: Int): LiveData<MovieEntity>
}
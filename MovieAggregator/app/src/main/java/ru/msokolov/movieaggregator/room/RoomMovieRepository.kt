package ru.msokolov.movieaggregator.room

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Completable
import ru.msokolov.movieaggregator.room.entities.MovieEntity

class RoomMovieRepository(
    private val movieDao: MovieDao
) : MovieRepository {

    override fun insertMovieToFavourites(movieEntity: MovieEntity): Completable {
        return movieDao.insert(movieEntity)
    }

    override fun deleteMovieFromFavourites(movieId: Int): Completable {
        return movieDao.delete(movieId)
    }

    override fun getAllMoviesFromFavourites(): LiveData<List<MovieEntity>> {
        return movieDao.getAll()
    }

    override fun getMovieFromFavourites(movieId: Int): LiveData<MovieEntity> {
        return movieDao.getOne(movieId)
    }
}
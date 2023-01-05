package ru.msokolov.movieaggregator.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import ru.msokolov.movieaggregator.room.entities.MovieEntity

@Dao
interface MovieDao {

    @Insert
    fun insert(movieEntity: MovieEntity): Completable

    @Delete
    fun delete(movieId: Int): Completable

    @Query("SELECT * FROM movie_table")
    fun getAll(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movie_table WHERE id=:movieId")
    fun getOne(movieId: Int): LiveData<MovieEntity>
}
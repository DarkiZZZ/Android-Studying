package ru.msokolov.movieaggregator.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.msokolov.movieaggregator.room.entities.MovieEntity

@Database(entities = [MovieEntity::class],
    version = 1,
    exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}

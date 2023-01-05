package ru.msokolov.movieaggregator.room

import android.content.Context
import androidx.room.Room

object Repositories {

    private lateinit var applicationContext: Context

    private val database: AppDatabase by lazy<AppDatabase> {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db")
            .createFromAsset("initial_database.db")
            .build()
    }

    val movieRepository: MovieRepository by lazy {
        RoomMovieRepository(database.getMovieDao())
    }

    fun init(context: Context) {
        applicationContext = context
    }
}
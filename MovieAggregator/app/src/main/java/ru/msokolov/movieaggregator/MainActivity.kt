package ru.msokolov.movieaggregator

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import ru.msokolov.movieaggregator.adapter.MovieAdapter
import ru.msokolov.movieaggregator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels<MainViewModel>()

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()

        mainViewModel.loadMovies()
        mainViewModel.movies.observe(this, Observer { movies ->
            movieAdapter.isLoadingMovies = true
            movieAdapter.setMovieList(movies.toMutableList())
        })
    }


    private fun initAdapter(){
        binding.recyclerView.layoutManager = GridLayoutManager(
            this@MainActivity, 2)
        movieAdapter = MovieAdapter()
        binding.recyclerView.adapter = movieAdapter
        movieAdapter.onReachEndListener = object : MovieAdapter.OnReachEndListener{
            override fun onReachEnd() {
                mainViewModel.loadMovies()
            }
        }
    }
}
package ru.msokolov.movieaggregator

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import ru.msokolov.movieaggregator.adapter.MovieAdapter
import ru.msokolov.movieaggregator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()

        mainViewModel.movies.observe(this, Observer { movies ->
            movieAdapter.isLoadingMovies = true
            movieAdapter.setMovieList(movies.toMutableList())
        })
        mainViewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading){
                binding.progressBar.visibility = View.VISIBLE
            } else{
                binding.progressBar.visibility = View.GONE
            }
        })
    }


    private fun initAdapter(){
        binding.recyclerView.layoutManager = GridLayoutManager(
            this@MainActivity, 2)
        movieAdapter = MovieAdapter()
        binding.recyclerView.adapter = movieAdapter
        movieAdapter.onReachEndListener = object : MovieAdapter.OnReachEndListener {
            override fun onReachEnd() {
                mainViewModel.loadMovies()
            }
        }
        movieAdapter.onItemClickListener = { movie ->
            val intent = Intent(this@MainActivity, MovieDetailActivity::class.java)
            intent.putExtra(Constants.VALUE_KEY, movie)
            startActivity(intent)
        }
    }
}
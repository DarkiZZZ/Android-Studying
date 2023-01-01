package ru.msokolov.movieaggregator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.msokolov.movieaggregator.databinding.ActivityMainBinding
import ru.msokolov.movieaggregator.retrofit.ApiFactory

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels<MainViewModel>()

    private lateinit var adapter: MovieAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MovieAdapter()
        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(
                this@MainActivity, 2)
        }

        mainViewModel.movies.observe(this, Observer { movies ->
             adapter.movies = movies
        })
        mainViewModel.loadMovies()
    }
}
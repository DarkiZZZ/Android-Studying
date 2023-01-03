package ru.msokolov.movieaggregator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import ru.msokolov.movieaggregator.databinding.ActivityMovieDetailBinding
import ru.msokolov.movieaggregator.retrofit.entities.Movie

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = intent.getSerializableExtra(Constants.VALUE_KEY) as Movie

        Glide.with(this)
            .load(movie.poster.url)
            .into(binding.posterImageView)
        binding.apply {
            titleTextView.text = movie.name
            yearTextView.text = movie.year.toString()
            descTextView.text = movie.description
        }
    }
}
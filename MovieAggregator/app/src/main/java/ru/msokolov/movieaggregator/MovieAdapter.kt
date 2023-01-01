package ru.msokolov.movieaggregator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.msokolov.movieaggregator.databinding.MovieItemBinding
import ru.msokolov.movieaggregator.retrofit.entities.Movie

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var movies: List<Movie> = ArrayList<Movie>()
        set(movies) {
            field = movies
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    inner class MovieViewHolder(private var binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            Glide.with(binding.root)
                .load(movie.poster.url)
                .into(binding.movieImage)

            val rating = movie.rating.kp
            binding.ratingTextView.text = rating.toString().dropLast(2)

            val ratingBackground = getRatingBackground(rating, binding.root.context)
            binding.ratingTextView.background = ratingBackground
        }
    }
}
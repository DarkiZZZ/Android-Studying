package ru.msokolov.movieaggregator.ui.show.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.msokolov.movieaggregator.databinding.MovieItemBinding
import ru.msokolov.movieaggregator.retrofit.entities.Movie

class MovieAdapter()
    : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var onItemClickListener: ((Movie) -> Unit)? = null

    var isLoadingMovies = false
    private var newMovies = mutableListOf<Movie>()
    private var oldMovies = mutableListOf<Movie>()

    var onReachEndListener: OnReachEndListener? = null

    fun setMovieList(movies: MutableList<Movie>){
        if (isLoadingMovies){
            if (oldMovies.isEmpty()){
                oldMovies.addAll(movies)
                newMovies.addAll(oldMovies)
                notifyDataSetChanged()
            }
            else{
                oldMovies.addAll(newMovies)
                newMovies.addAll(movies)
                notifyItemRangeChanged(oldMovies.size, newMovies.size)
            }
            isLoadingMovies = false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(newMovies[position])
        if (position == newMovies.size - 4 && onReachEndListener != null){
            onReachEndListener!!.onReachEnd()
        }
    }

    override fun getItemCount(): Int = newMovies.size

    inner class MovieViewHolder(private var binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            Glide.with(binding.root)
                .load(movie.poster.url)
                .into(binding.movieImage)

            val rating = getNormalizeRating(movie.rating.kp)
            val ratingBackground = getRatingBackground(rating.toDouble(), binding.root.context)

            binding.ratingTextView.text = rating
            binding.ratingTextView.background = ratingBackground
        }

        init {
            binding.movieImage.setOnClickListener {
                onItemClickListener?.invoke(newMovies[adapterPosition])
            }
        }
    }

    interface OnReachEndListener{
        fun onReachEnd()
    }
}
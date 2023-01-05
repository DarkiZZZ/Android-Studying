package ru.msokolov.movieaggregator.ui.details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.msokolov.movieaggregator.databinding.ReviewItemBinding
import ru.msokolov.movieaggregator.retrofit.entities.Review

class ReviewAdapter
    : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>(){

    var reviewsList: List<Review> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(
            ReviewItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviewsList[position])
    }

    override fun getItemCount(): Int = reviewsList.size


    inner class ReviewViewHolder(private var binding: ReviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) {
            val pair = getReviewDrawableColor(review, binding.root.context)
            binding.apply {
                reviewView.background = pair.first

                descTextView.text = review.text
                authorTextView.text = review.author
                descTextView.setTextColor(pair.second)
                authorTextView.setTextColor(pair.second)

            }
        }
    }
}
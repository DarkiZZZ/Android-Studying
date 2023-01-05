package ru.msokolov.movieaggregator.ui.details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.msokolov.movieaggregator.databinding.TrailerItemBinding
import ru.msokolov.movieaggregator.retrofit.entities.Trailer

class TrailerAdapter
    : RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>(){

    var onItemClickListener: ((Trailer) -> Unit)? = null
    var trailersList: List<Trailer> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        return TrailerViewHolder(
            TrailerItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        holder.bind(trailersList[position])
    }

    override fun getItemCount(): Int = trailersList.size


    inner class TrailerViewHolder(private var binding: TrailerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(trailer: Trailer) {
            binding.trailerTextView.text = getShortageTrailerName(trailer)
        }

        init {
            binding.trailerView.setOnClickListener {
                onItemClickListener?.invoke(trailersList[adapterPosition])
            }
        }
    }
}
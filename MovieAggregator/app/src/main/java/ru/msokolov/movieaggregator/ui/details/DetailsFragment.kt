package ru.msokolov.movieaggregator.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import ru.msokolov.movieaggregator.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private val detailsViewModel: DetailsViewModel by viewModels()
    private lateinit var binding: FragmentDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val movie = args.movie

        Glide.with(this)
            .load(movie.poster.url)
            .into(binding.posterImageView)
        binding.apply {
            titleTextView.text = movie.name
            yearTextView.text = movie.year.toString()
            descTextView.text = movie.description
        }

        return binding.root
    }

}
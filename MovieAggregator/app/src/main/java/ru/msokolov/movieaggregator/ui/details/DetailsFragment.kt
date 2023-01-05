package ru.msokolov.movieaggregator.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import ru.msokolov.movieaggregator.databinding.FragmentDetailsBinding
import ru.msokolov.movieaggregator.ui.details.adapter.TrailerAdapter

class DetailsFragment : Fragment() {

    private val detailsViewModel: DetailsViewModel by viewModels()
    private lateinit var binding: FragmentDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var trailerAdapter: TrailerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val movie = args.movie
        detailsViewModel.loadTrailers(movie)

        detailsViewModel.trailers.observe(requireActivity(), Observer { trailers ->
            trailerAdapter.trailersList = trailers
        })

        initAdapter()

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

    private fun initAdapter(){
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        trailerAdapter = TrailerAdapter()
        binding.recyclerView.adapter = trailerAdapter

        trailerAdapter.onItemClickListener = { trailer ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(trailer.url)
            startActivity(intent)
        }
    }
}
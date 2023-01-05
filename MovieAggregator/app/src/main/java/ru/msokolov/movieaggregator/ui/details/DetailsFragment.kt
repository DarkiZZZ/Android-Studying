package ru.msokolov.movieaggregator.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import ru.msokolov.movieaggregator.databinding.FragmentDetailsBinding
import ru.msokolov.movieaggregator.retrofit.entities.Movie
import ru.msokolov.movieaggregator.room.Repositories
import ru.msokolov.movieaggregator.room.utils.viewModelCreator
import ru.msokolov.movieaggregator.ui.details.adapters.ReviewAdapter
import ru.msokolov.movieaggregator.ui.details.adapters.TrailerAdapter

class DetailsFragment : Fragment() {

    private val viewModel by viewModelCreator{DetailsViewModel(Repositories.movieRepository)}
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var currentMovie: Movie
    private val args by navArgs<DetailsFragmentArgs>()

    private lateinit var trailerAdapter: TrailerAdapter
    private lateinit var reviewAdapter: ReviewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentMovie = args.movie
        loadData(currentMovie)
        subscribeOnLiveData()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        initTrailerAdapter()
        initReviewAdapter()

        Glide.with(this)
            .load(currentMovie.poster.url)
            .into(binding.posterImageView)
        updateUi(binding)

        return binding.root
    }

    private fun updateUi(binding: FragmentDetailsBinding){
        binding.apply {
            titleTextView.text = currentMovie.name
            yearTextView.text = currentMovie.year.toString()
            descTextView.text = currentMovie.description
        }
    }

    private fun initTrailerAdapter(){
        binding.recyclerViewTrailers.layoutManager = LinearLayoutManager(requireContext())
        trailerAdapter = TrailerAdapter()
        binding.recyclerViewTrailers.adapter = this.trailerAdapter

        this.trailerAdapter.onItemClickListener = { trailer ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(trailer.url)
            startActivity(intent)
        }
    }

    private fun initReviewAdapter(){
        binding.recyclerViewReviews.layoutManager = LinearLayoutManager(requireContext())
        reviewAdapter = ReviewAdapter()
        binding.recyclerViewReviews.adapter = this.reviewAdapter
    }

    private fun loadData(currentMovie: Movie){
        viewModel.loadTrailers(currentMovie)
        viewModel.loadReviews(currentMovie)
    }

    private fun subscribeOnLiveData(){
        viewModel.trailers.observe(requireActivity(), Observer { trailers ->
            trailerAdapter.trailersList = trailers
        })
        viewModel.reviews.observe(requireActivity(), Observer { reviews ->
            reviewAdapter.reviewsList = reviews
        })
    }
}
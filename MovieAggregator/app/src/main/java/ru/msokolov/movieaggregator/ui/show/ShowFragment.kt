package ru.msokolov.movieaggregator.ui.show

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ru.msokolov.movieaggregator.databinding.FragmentShowBinding
import ru.msokolov.movieaggregator.room.Repositories
import ru.msokolov.movieaggregator.room.utils.viewModelCreator
import ru.msokolov.movieaggregator.ui.show.adapter.MovieAdapter

class ShowFragment : Fragment() {

    private val viewModel by viewModelCreator { ShowViewModel(Repositories.movieRepository) }
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var binding: FragmentShowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowBinding.inflate(inflater, container,false)
        initAdapter()

        viewModel.movies.observe(requireActivity(), Observer { movies ->
            movieAdapter.isLoadingMovies = true
            movieAdapter.setMovieList(movies.toMutableList())
        })
        viewModel.isLoading.observe(requireActivity(), Observer { isLoading ->
            if (isLoading){
                binding.progressBar.visibility = View.VISIBLE
            } else{
                binding.progressBar.visibility = View.GONE
            }
        })
        return binding.root
    }

    private fun initAdapter(){
        binding.recyclerView.layoutManager = GridLayoutManager(
            requireContext(), 2)
        movieAdapter = MovieAdapter()
        binding.recyclerView.adapter = movieAdapter
        movieAdapter.onReachEndListener = object : MovieAdapter.OnReachEndListener {
            override fun onReachEnd() {
                viewModel.loadMovies()
            }
        }
        movieAdapter.onItemClickListener = { movie ->
            findNavController().navigate(ShowFragmentDirections
                .actionShowFragmentToDetailsFragment(movie))
        }
    }

}
package ru.msokolov.movieaggregator.ui.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ru.msokolov.movieaggregator.databinding.FragmentFavouritesBinding
import ru.msokolov.movieaggregator.room.Repositories
import ru.msokolov.movieaggregator.room.utils.viewModelCreator

class FavouritesFragment : Fragment() {

    private val viewModel by viewModelCreator { FavouritesViewModel(Repositories.movieRepository) }

    private lateinit var binding: FragmentFavouritesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}
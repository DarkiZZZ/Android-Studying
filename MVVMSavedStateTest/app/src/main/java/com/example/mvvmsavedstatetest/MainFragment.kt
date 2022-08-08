package com.example.mvvmsavedstatetest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mvvmsavedstatetest.databinding.FragmentMainBinding
import kotlin.random.Random

class MainFragment: Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container,false)

        createAndRenderSquares()
        binding.generateColorImageView.setOnClickListener { createAndRenderSquares() }

        return binding.root
    }

    private fun createAndRenderSquares(){
        renderSquares(createSquares())
    }

    private fun createSquares() : Squares {
        return Squares(
            size = Random.nextInt(5, 11),
            colorProducer = {-Random.nextInt(0xFFFFFF)}
        )
    }

    private fun renderSquares(squares: Squares) = with(binding) {

    }
}
package com.example.mvvmsavedstatetest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import androidx.core.view.setMargins
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
        colorsContainer.removeAllViews()
        val ids = squares.colors.indices.map { View.generateViewId() }
        for (i in squares.colors.indices) {
            val row = i / squares.size
            val column = i % squares.size

            val view = View(context)
            view.setBackgroundColor(squares.colors[i])
            view.id = ids[i]

            val params = LayoutParams(0 , 0)
            params.setMargins(resources.getDimensionPixelSize(R.dimen.space))
            view.layoutParams = params

            if (column == 0) params.startToStart = LayoutParams.PARENT_ID
            else params.startToEnd = ids[i - 1]

            if (column == squares.size - 1) params.endToEnd = LayoutParams.PARENT_ID
            else params.endToStart = ids[i + 1]

            if (row == 0) params.topToTop = LayoutParams.PARENT_ID
            else params.topToBottom = ids[i - squares.size]

            if (row == squares.size - 1) params.bottomToBottom = LayoutParams.PARENT_ID
            else params.bottomToTop = ids[i + squares.size]

            colorsContainer.addView(view)
        }
    }
}
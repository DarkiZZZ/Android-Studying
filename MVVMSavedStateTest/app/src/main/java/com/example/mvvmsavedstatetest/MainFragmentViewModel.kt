package com.example.mvvmsavedstatetest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class MainFragmentViewModel : ViewModel() {

    private val _squares = MutableLiveData<Squares>()
    val squares: LiveData<Squares> = _squares

    init {
        generateSquares()
    }

    fun generateSquares(){
        _squares.value = createSquares()
    }

    private fun createSquares() : Squares{
        return Squares(
            size = Random.nextInt(5, 11),
            colorProducer = {-Random.nextInt(0xFFFFFF)}
        )
    }
}
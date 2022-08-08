package com.example.mvvmsavedstatetest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class MainFragmentViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _squares = savedStateHandle.getLiveData<Squares>("squares")
    val squares: LiveData<Squares> = _squares

    init {
        if(!savedStateHandle.contains("squares")){
            savedStateHandle["squares"] = createSquares()
        }
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
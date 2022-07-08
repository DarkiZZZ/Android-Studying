package com.example.tictactoecustomviewapp

import android.content.Context
import android.util.AttributeSet
import android.view.View

class TicTacToeView(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int) : View(context, attributeSet, defStyleAttr, defStyleRes) {

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) :
            this(context, attributeSet, defStyleAttr, 0)
    constructor(context: Context, attributeSet: AttributeSet?) :
            this(context, attributeSet, 0)
    constructor(context: Context) :
            this(context, null)

    init {
        if (attributeSet != null){
            initializeAttributeSet(attributeSet, defStyleAttr, defStyleRes)
        }
    }

    private fun initializeAttributeSet(attributeSet: AttributeSet?,
                                       defStyleAttr: Int,
                                       defStyleRes: Int){

    }
}
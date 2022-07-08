package com.example.tictactoecustomviewapp

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import kotlin.properties.Delegates

class TicTacToeView(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int) : View(context, attributeSet, defStyleAttr, defStyleRes) {

    private var crossColor: Int by Delegates.notNull<Int>()
    private var zeroColor: Int by Delegates.notNull<Int>()
    private var gridColor: Int by Delegates.notNull<Int>()

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) :
            this(context, attributeSet, defStyleAttr, R.style.DefaultTicTacToeStyle)
    constructor(context: Context, attributeSet: AttributeSet?) :
            this(context, attributeSet, R.attr.TicTacToeStyle)
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
        val typedArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.TicTacToeView,
            defStyleAttr,
            defStyleRes)
        crossColor = typedArray.getColor(R.styleable.TicTacToeView_crossColor, CROSS_DEFAULT_COLOR)
        zeroColor = typedArray.getColor(R.styleable.TicTacToeView_zeroColor, ZERO_DEFAULT_COLOR)
        gridColor = typedArray.getColor(R.styleable.TicTacToeView_gridColor, GRID_DEFAULT_COLOR)


        typedArray.recycle()
    }

    private fun initializeDefaultColors(){
        crossColor = CROSS_DEFAULT_COLOR
        zeroColor = ZERO_DEFAULT_COLOR
        gridColor = GRID_DEFAULT_COLOR
    }

    companion object{
        private const val CROSS_DEFAULT_COLOR = Color.BLUE
        private const val ZERO_DEFAULT_COLOR = Color.RED
        private const val GRID_DEFAULT_COLOR = Color.GRAY
    }
}
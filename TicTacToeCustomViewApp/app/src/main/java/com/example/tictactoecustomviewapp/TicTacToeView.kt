package com.example.tictactoecustomviewapp

import android.content.Context
import android.graphics.Color
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import kotlin.math.max
import kotlin.math.min
import kotlin.properties.Delegates

class TicTacToeView(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int) : View(context, attributeSet, defStyleAttr, defStyleRes) {


    var ticTacToeField: TicTacToeField? = null
        set(value) {
            field?.listeners?.remove { listener }
            field = value
            value?.listeners?.add { listener  }
            updateViewSize()
            requestLayout()
            invalidate()
        }

    private var crossColor: Int by Delegates.notNull<Int>()
    private var zeroColor: Int by Delegates.notNull<Int>()
    private var gridColor: Int by Delegates.notNull<Int>()

    private var fieldRect = RectF(0f, 0f, 0f, 0f)
    private var cellSize: Float = 0f
    private var cellPadding: Float = 0f

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) :
            this(context, attributeSet, defStyleAttr, R.style.DefaultTicTacToeStyle)
    constructor(context: Context, attributeSet: AttributeSet?) :
            this(context, attributeSet, R.attr.TicTacToeStyle)
    constructor(context: Context) :
            this(context, null)

    init {
        if (attributeSet != null){
            initializeAttributeSet(attributeSet, defStyleAttr, defStyleRes)
        } else{
            initializeDefaultColors()
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

    private val listener: OnFieldChangeListener = {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minWidth = suggestedMinimumWidth + paddingLeft + paddingRight
        val minHeight = suggestedMinimumHeight + paddingStart + paddingEnd

        val cellSizeInPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DESIRED_CELL_SIZE,
            resources.displayMetrics).toInt()
        val rows = ticTacToeField?.rows ?: 0
        val columns = ticTacToeField?.columns ?: 0

        val desiredWidth = max(minWidth, columns * cellSizeInPixels + paddingLeft + paddingRight)
        val desiredHeight = max(minHeight, rows * cellSizeInPixels + paddingStart + paddingEnd)

        setMeasuredDimension(
            resolveSize(desiredWidth, widthMeasureSpec),
            resolveSize(desiredHeight, heightMeasureSpec)
        )
    }

    private fun initializeDefaultColors(){
        crossColor = CROSS_DEFAULT_COLOR
        zeroColor = ZERO_DEFAULT_COLOR
        gridColor = GRID_DEFAULT_COLOR
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ticTacToeField?.listeners?.add { listener }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ticTacToeField?.listeners?.remove { listener }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private fun updateViewSize(){
        val field = this.ticTacToeField ?: return
        val safeWidth = width - paddingLeft - paddingRight
        val safeHeight = height - paddingStart - paddingEnd

        val cellWidth = safeWidth / field.columns.toFloat()
        val cellHeight = safeHeight / field.rows.toFloat()

        cellSize = min(cellWidth, cellHeight)
        cellPadding = cellSize * 0.2f

        val fieldWidth = cellSize * field.columns
        val fieldHeight = cellSize * field.rows

        fieldRect.left = paddingLeft + (safeWidth - fieldWidth) / 2
        fieldRect.top = paddingTop + (safeHeight - fieldHeight) / 2
        fieldRect.right = fieldRect.left + fieldWidth
        fieldRect.bottom = fieldRect.top + fieldHeight
    }

    companion object{
        private const val CROSS_DEFAULT_COLOR = Color.BLUE
        private const val ZERO_DEFAULT_COLOR = Color.RED
        private const val GRID_DEFAULT_COLOR = Color.GRAY
        private const val DESIRED_CELL_SIZE = 50f
    }
}
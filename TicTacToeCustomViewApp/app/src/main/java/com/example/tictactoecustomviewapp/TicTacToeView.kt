package com.example.tictactoecustomviewapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min
import kotlin.properties.Delegates

typealias OnCellActionListener = (row: Int, column: Int, field: TicTacToeField) -> Unit

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

    var actionListener: OnCellActionListener? = null

    private var crossColor by Delegates.notNull<Int>()
    private var zeroColor by Delegates.notNull<Int>()
    private var gridColor by Delegates.notNull<Int>()

    private var fieldRect = RectF(0f, 0f, 0f, 0f)
    private var cellSize: Float = 0f
    private var cellPadding: Float = 0f
    private var cellRect = RectF()


    private var currentRow: Int = -1
    private var currentColumn: Int = -1

    private lateinit var crossPaint: Paint
    private lateinit var zeroPaint: Paint
    private lateinit var gridPaint: Paint
    private lateinit var currentCellPaint: Paint

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
        initPaints()
        if(isInEditMode){
            ticTacToeField = TicTacToeField(12, 10)
            ticTacToeField?.setCell(4, 4, Cell.CROSS)
            ticTacToeField?.setCell(3, 4, Cell.ZERO)
        }
        isFocusable = true
        isClickable = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            defaultFocusHighlightEnabled = false
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

    private fun initPaints() {
        crossPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        crossPaint.color = crossColor
        crossPaint.style = Paint.Style.STROKE
        crossPaint.strokeWidth =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3f, resources.displayMetrics)

        zeroPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        zeroPaint.color = zeroColor
        zeroPaint.style = Paint.Style.STROKE
        zeroPaint.strokeWidth =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3f, resources.displayMetrics)

        gridPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        gridPaint.color = gridColor
        gridPaint.style = Paint.Style.STROKE
        gridPaint.strokeWidth =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, resources.displayMetrics)

        currentCellPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        currentCellPaint.color = Color.rgb(230, 230, 230)
        currentCellPaint.style = Paint.Style.FILL
    }

    private val listener: OnFieldChangeListener = {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minWidth = suggestedMinimumWidth + paddingLeft + paddingRight
        val minHeight = suggestedMinimumHeight + paddingTop + paddingBottom

        val cellSizeInPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DESIRED_CELL_SIZE,
            resources.displayMetrics).toInt()
        val rows = ticTacToeField?.rows ?: 0
        val columns = ticTacToeField?.columns ?: 0

        val desiredWidth = max(minWidth, columns * cellSizeInPixels + paddingLeft + paddingRight)
        val desiredHeight = max(minHeight, rows * cellSizeInPixels + paddingTop + paddingBottom)

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
        updateViewSize()
    }

    private fun updateViewSize(){
        val field = this.ticTacToeField ?: return
        val safeWidth = width - paddingLeft - paddingRight
        val safeHeight = height - paddingTop - paddingBottom

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

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (ticTacToeField == null) return
        if (cellSize == 0f) return
        if (fieldRect.width() <= 0) return
        if (fieldRect.height() <= 0) return

        drawCells(canvas)
        drawCurrentCell(canvas)
        drawGrid(canvas)

    }

    private fun drawCurrentCell(canvas: Canvas) {
        val field = this.ticTacToeField ?: return
        if (currentRow < 0 && currentColumn < 0 && currentRow >= field.rows
            && currentColumn >= field.columns) return

        val cell = getCellRect(currentRow, currentColumn)
        canvas.drawRect(
            cell.left - cellPadding,
            cell.top - cellPadding,
            cell.right + cellPadding,
            cell.bottom + cellPadding,
            currentCellPaint
        )
    }

    private fun drawGrid(canvas: Canvas){
        val field = this.ticTacToeField ?: return

        val xStart = fieldRect.left
        val xEnd = fieldRect.right
        for (line in 0..field.rows){
            val y = fieldRect.top + cellSize * line
            canvas.drawLine(xStart, y, xEnd, y, gridPaint )
        }

        val yStart = fieldRect.top
        val yEnd = fieldRect.bottom
        for (line in 0..field.columns){
            val x = fieldRect.left + cellSize * line
            canvas.drawLine(x, yStart, x, yEnd, gridPaint )
        }
    }

    private fun drawCells(canvas: Canvas){
        val field = this.ticTacToeField ?: return

        for(row in 0 until field.rows){
            for (column in 0 until field.columns){
                val cell = field.getCell(row, column)
                if (cell == Cell.CROSS) {
                    drawCross(row, column, canvas)
                }
                else if (cell == Cell.ZERO) {
                    drawZero(row, column, canvas)
                }
            }
        }
    }

    private fun drawZero(row: Int, column: Int, canvas: Canvas) {
        val cellRect = getCellRect(row, column)
        canvas.drawCircle(
            cellRect.centerX(),
            cellRect.centerY(),
        cellRect.width() / 2,
            zeroPaint)
    }

    private fun getCellRect(row: Int, column: Int): RectF {
        cellRect.left = fieldRect.left + column * cellSize + cellPadding
        cellRect.top = fieldRect.top + row * cellSize + cellPadding
        cellRect.right = cellRect.left + cellSize - cellPadding * 2
        cellRect.bottom = cellRect.top + cellSize - cellPadding * 2
        return cellRect
    }

    private fun drawCross(row: Int, column: Int, canvas: Canvas) {
        val cellRect = getCellRect(row, column)
        canvas.drawLine(cellRect.left, cellRect.top, cellRect.right, cellRect.bottom, crossPaint)
        canvas.drawLine(cellRect.right, cellRect.top, cellRect.left, cellRect.bottom, crossPaint)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                updateCurrentCell(event)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                updateCurrentCell(event)
                return true
            }
            MotionEvent.ACTION_UP -> {
                return performClick()
            }
        }
        return false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return when(keyCode) {
            KeyEvent.KEYCODE_DPAD_DOWN -> moveCurrentCell(1, 0)
            KeyEvent.KEYCODE_DPAD_LEFT -> moveCurrentCell(0, -1)
            KeyEvent.KEYCODE_DPAD_RIGHT -> moveCurrentCell(0, 1)
            KeyEvent.KEYCODE_DPAD_UP -> moveCurrentCell(-1, 0)
            else -> super.onKeyDown(keyCode, event)
        }
    }

    private fun moveCurrentCell(rowDiff: Int, columnDiff: Int): Boolean {
        val field = this.ticTacToeField ?: return false
        if (currentRow < 0 || currentColumn < 0 || currentRow >= field.rows
            || currentColumn >= field.columns) {
            currentRow = 0
            currentColumn = 0
            invalidate()
            return true
        } else {
            if (currentColumn + columnDiff < 0) return false
            if (currentColumn + columnDiff >= field.columns) return false
            if (currentRow + rowDiff < 0) return false
            if (currentRow + rowDiff >= field.rows) return false

            currentColumn += columnDiff
            currentRow += rowDiff
            invalidate()
            return true
        }
    }

    override fun performClick(): Boolean {
        super.performClick()
        val field = this.ticTacToeField ?: return false
        val row = currentRow
        val column = currentColumn
        if (row >= 0 && column >= 0 && row < field.rows && column < field.columns) {
            actionListener?.invoke(row, column, field)
            return true
        }
        return false
    }

    private fun updateCurrentCell(event: MotionEvent) {
        val field = this.ticTacToeField ?: return
        val row = getRow(event)
        val column = getColumn(event)
        if (row >= 0 && column >= 0 && row < field.rows && column < field.columns) {
            if (currentRow != row || currentColumn != column) {
                currentRow = row
                currentColumn = column
                invalidate()
            }
        } else {
            currentRow = -1
            currentColumn = -1
            invalidate()
        }
    }

    private fun getRow(event: MotionEvent): Int {
        return floor((event.y - fieldRect.top) / cellSize).toInt()
    }

    private fun getColumn(event: MotionEvent): Int {
        return floor((event.x - fieldRect.left) / cellSize).toInt()
    }

    companion object{
        private const val CROSS_DEFAULT_COLOR = Color.BLUE
        private const val ZERO_DEFAULT_COLOR = Color.RED
        private const val GRID_DEFAULT_COLOR = Color.BLACK
        private const val DESIRED_CELL_SIZE = 50f
    }
}
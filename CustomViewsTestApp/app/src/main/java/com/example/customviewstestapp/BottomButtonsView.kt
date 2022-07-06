package com.example.customviewstestapp

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.customviewstestapp.databinding.PartButtonsBinding

class BottomButtonsView(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ): ConstraintLayout(context,  attrs, defStyleAttr, defStyleRes) {

    private lateinit var binding: PartButtonsBinding

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            this(context, attrs, defStyleAttr, 0)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context): this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.part_buttons, this, true)
        binding = PartButtonsBinding.bind(this)
    }

    private fun initializeAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int){
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.BottomButtonsView,
            defStyleAttr,
            defStyleRes
        )

        with(binding){
            val positiveButtonText =
                typedArray.getString(R.styleable.BottomButtonsView_PositiveButtonText)
            applyButton.text = positiveButtonText ?:"OK"

            val negativeButtonText =
                typedArray.getString(R.styleable.BottomButtonsView_NegativeButtonText)
            cancelButton.text = negativeButtonText ?:"Cancel"

            val positiveButtonBackgroundColor =
                typedArray.getColor(R.styleable.BottomButtonsView_PositiveButtonTextColor, Color.BLACK)
            applyButton.backgroundTintList = ColorStateList.valueOf(positiveButtonBackgroundColor)

            val negativeButtonBackgroundColor =
                typedArray.getColor(R.styleable.BottomButtonsView_NegativeButtonTextColor, Color.WHITE)
            cancelButton.backgroundTintList = ColorStateList.valueOf(negativeButtonBackgroundColor)

            val isProgressMode =
                typedArray.getBoolean(R.styleable.BottomButtonsView_progressMode, false)
            if (isProgressMode){
                applyButton.visibility = INVISIBLE
                cancelButton.visibility = INVISIBLE
                progressBar.visibility = VISIBLE
            } else{
                applyButton.visibility = VISIBLE
                cancelButton.visibility = VISIBLE
                progressBar.visibility = GONE
            }
        }
        typedArray.recycle()
    }
}
package com.example.customviewstestapp

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.customviewstestapp.databinding.PartButtonsBinding

enum class BottomButtonAction{
    POSITIVE, NEGATIVE
}
typealias OnBottomButtonActionListener = (BottomButtonAction) -> Unit

class BottomButtonsView(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ): ConstraintLayout(context,  attrs, defStyleAttr, defStyleRes) {

    private var binding: PartButtonsBinding
    private var listener: OnBottomButtonActionListener? = null

    var isProgressMode: Boolean = false
        set(value) {
            field = value
            if (isProgressMode){
                with(binding){
                    applyButton.visibility = INVISIBLE
                    cancelButton.visibility = INVISIBLE
                    progressBar.visibility = VISIBLE
                }
            } else{
                with(binding){
                    applyButton.visibility = VISIBLE
                    cancelButton.visibility = VISIBLE
                    progressBar.visibility = GONE
                }
            }
        }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            this(context, attrs, defStyleAttr, 0)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context): this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.part_buttons, this, true)
        binding = PartButtonsBinding.bind(this)
        initializeAttributes(attrs, defStyleAttr, defStyleRes)
        initListeners()
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
            setApplyButtonText(positiveButtonText)

            val negativeButtonText =
                typedArray.getString(R.styleable.BottomButtonsView_NegativeButtonText)
            setCancelButtonText(negativeButtonText)

            val positiveButtonBackgroundColor =
                typedArray.getColor(R.styleable.BottomButtonsView_PositiveButtonTextColor, Color.BLACK)
            applyButton.backgroundTintList = ColorStateList.valueOf(positiveButtonBackgroundColor)

            val negativeButtonBackgroundColor =
                typedArray.getColor(R.styleable.BottomButtonsView_NegativeButtonTextColor, Color.RED)
            cancelButton.backgroundTintList = ColorStateList.valueOf(negativeButtonBackgroundColor)

            this@BottomButtonsView.isProgressMode =
                typedArray.getBoolean(R.styleable.BottomButtonsView_progressMode, false)
        }
        typedArray.recycle()
    }

    private fun initListeners(){
        with(binding){
            applyButton.setOnClickListener {
                this@BottomButtonsView.listener?.invoke(BottomButtonAction.POSITIVE)
            }
            cancelButton.setOnClickListener {
                this@BottomButtonsView.listener?.invoke(BottomButtonAction.NEGATIVE)
            }
        }
    }

    fun setListener(listener: OnBottomButtonActionListener){
        this.listener = listener
    }

    fun setApplyButtonText(text: String?){
        binding.applyButton.text = text ?:"OK"
    }

    fun setCancelButtonText(text: String?){
        binding.cancelButton.text = text ?:"Cancel"
    }
}
package ru.msokolov.movieaggregator

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

fun getRatingBackground(rating: Double, context:Context): Drawable{
    val backgroundId: Int
    if (rating > 7){
        backgroundId = R.drawable.circle_green
    }
    else if (rating > 5){
        backgroundId = R.drawable.circle_orange
    }
    else{
        backgroundId = R.drawable.circle_red
    }

    return ContextCompat.getDrawable(context, backgroundId)!!
}
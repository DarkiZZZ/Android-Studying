package ru.msokolov.movieaggregator.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import ru.msokolov.movieaggregator.R

internal fun getRatingBackground(rating: Double, context:Context): Drawable{
    val backgroundId: Int =
        if (rating > 7){
        R.drawable.circle_green
    }
    else if (rating > 5){
        R.drawable.circle_orange
    }
    else{
        R.drawable.circle_red
    }
    return ContextCompat.getDrawable(context, backgroundId)!!
}

internal fun getNormalizeRating(oldRating: Double): String{
    var newRating = oldRating.toString().dropLast(2)
    return if (newRating.length < 3){
        newRating += "0"
        newRating
    }
    else{
        newRating
    }
}


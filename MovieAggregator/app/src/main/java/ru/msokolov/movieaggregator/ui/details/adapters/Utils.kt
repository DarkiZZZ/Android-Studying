package ru.msokolov.movieaggregator.ui.details.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import ru.msokolov.movieaggregator.R
import ru.msokolov.movieaggregator.retrofit.entities.Review
import ru.msokolov.movieaggregator.retrofit.entities.Trailer

internal fun getShortageTrailerName(trailer: Trailer): String{
    val nameLength = trailer.name.length
    return if (nameLength > 47) {
        trailer.name
            .removeRange(47 until nameLength)
            .padEnd(50, '.')
    }
    else{
        trailer.name
    }
}

internal fun getReviewDrawableColor(review: Review, context: Context): Pair<Drawable, Int>{
    if (review.type == "Позитивный"){
        return ContextCompat
            .getDrawable(context, R.drawable.review_background_good)!! to
                ContextCompat.getColor(context, R.color.black)
    }
    else if(review.type == "Негативный"){
        return ContextCompat
            .getDrawable(context, R.drawable.review_background_bad)!! to
                ContextCompat.getColor(context, R.color.white)
    }
    else{
        return ContextCompat
            .getDrawable(context, R.drawable.review_background_neutral)!! to
                ContextCompat.getColor(context, R.color.black)
    }

}
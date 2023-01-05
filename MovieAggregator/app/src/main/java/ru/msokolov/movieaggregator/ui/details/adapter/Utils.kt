package ru.msokolov.movieaggregator.ui.details.adapter

import ru.msokolov.movieaggregator.retrofit.entities.Trailer

fun getShortageTrailerName(trailer: Trailer): String{
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
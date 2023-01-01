package ru.msokolov.movieaggregator.retrofit

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.msokolov.movieaggregator.retrofit.entities.ApiResponse

interface ApiService {

    @GET("movie?field=rating.kp&search=4-10&field=year&search=2017-2020&field=typeNumber&search=1&sortField=year&sortType=1&sortField=votes.imdb&sortType=-1&token=BCT6RGP-C3DMMCP-KE0PJ6K-VGYEQ7K")
    fun loadMovies(@Query("page") page: Int): Single<ApiResponse>
}
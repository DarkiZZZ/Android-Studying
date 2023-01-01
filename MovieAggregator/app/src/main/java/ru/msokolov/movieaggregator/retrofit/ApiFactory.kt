package ru.msokolov.movieaggregator.retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    private var ourApiService: ApiService? = null

    val apiService: ApiService
        get() {
            if (ourApiService == null){
                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl("https://api.kinopoisk.dev/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build()
                ourApiService = retrofit.create(ApiService::class.java)
            }
            return ourApiService!!
        }

}
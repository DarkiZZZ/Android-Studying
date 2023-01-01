package ru.msokolov.movieaggregator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.msokolov.movieaggregator.retrofit.ApiFactory
import ru.msokolov.movieaggregator.retrofit.entities.Movie

class MainViewModel: ViewModel() {

    private var _movies: MutableLiveData<List<Movie>> = MutableLiveData()
    val movies: LiveData<List<Movie>> = _movies

    private var page: Int = 1

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadMovies(){
        val disposable: Disposable = ApiFactory.apiService.loadMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { apiResponse ->
                    page++
                    _movies.value = apiResponse.movies },
                { error -> Log.d(TAG, error.toString()) }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object{
        private const val TAG = "MainViewModel_TAG"
    }
}
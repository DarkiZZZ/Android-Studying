package ru.msokolov.movieaggregator.ui.show

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
import ru.msokolov.movieaggregator.room.MovieRepository

class ShowViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private var _movies: MutableLiveData<List<Movie>> = MutableLiveData()
    val movies: LiveData<List<Movie>> = _movies

    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private var page: Int = 1

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadMovies(){
        val loading = _isLoading.value
        if(loading != null && loading){
            return
        }
        val disposable: Disposable = ApiFactory.apiService.loadMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _isLoading.value = true
            }
            .doAfterTerminate {
                _isLoading.value = false
            }
            .subscribe(
                { apiResponse ->
                    _movies.value = apiResponse.movies
                    page++
                },
                { error -> Log.d(TAG, error.toString()) }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


    companion object {
        private const val TAG = "MainViewModel_TAG"
    }

    init {
        loadMovies()
    }
}
package ru.msokolov.movieaggregator.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.msokolov.movieaggregator.retrofit.ApiFactory
import ru.msokolov.movieaggregator.retrofit.entities.Movie
import ru.msokolov.movieaggregator.retrofit.entities.Trailer

class DetailsViewModel : ViewModel() {

    private var _trailers: MutableLiveData<List<Trailer>> = MutableLiveData()
    val trailers: LiveData<List<Trailer>> = _trailers

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadTrailers(movie: Movie){
        val disposable =  ApiFactory.apiService.loadTrailers(movie.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { apiResponse ->
                return@map apiResponse.trailersList.trailers
            }
            .subscribe({ trailers ->
                _trailers.value = trailers
            }, { error ->
                Log.d(TAG, error.toString())
            })
        compositeDisposable.add(disposable)
    }

    companion object{
        private const val TAG = "DetailsViewModel"
    }
}
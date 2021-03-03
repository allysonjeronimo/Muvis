package com.allysonjeronimo.muvis.ui.moviedetails

import androidx.lifecycle.*
import com.allysonjeronimo.muvis.R
import com.allysonjeronimo.muvis.model.db.entity.Movie
import com.allysonjeronimo.muvis.repository.MovieRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _movieLiveData = MutableLiveData<Movie>()
    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    private val _errorOnLoadingLiveData = MutableLiveData<Int>()
    private val _errorOnUpdateLiveData = MutableLiveData<Int>()

    val movieLiveData: LiveData<Movie>
        get() = _movieLiveData

    val isLoadingLiveData:LiveData<Boolean>
        get() = _isLoadingLiveData

    val errorOnLoadingLiveData:LiveData<Int>
        get() = _errorOnLoadingLiveData

    val errorOnUpdateLiveData:LiveData<Int>
        get() = _errorOnUpdateLiveData

    fun togglesFavorite(movie:Movie){
        movie.togglesFavorite()
        repository.update(movie)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _isLoadingLiveData.postValue(true)}
            .doAfterTerminate{_isLoadingLiveData.postValue(false)}
            .subscribe(
                {
                    _movieLiveData.postValue(movie)
                },
                {
                    _errorOnUpdateLiveData.postValue(R.string.movie_details_error_on_update)
                }
            )
            .addTo(compositeDisposable)
    }

    fun loadMovieDetails(id:Int){
        repository.getMovie(id)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {_isLoadingLiveData.postValue(true)}
            .doAfterTerminate { _isLoadingLiveData.postValue(false) }
            .subscribe(
                {
                    _movieLiveData.postValue(it)
                },
                {
                    it.printStackTrace()
                    _errorOnLoadingLiveData.value = R.string.movie_details_error_on_loading
                }
            ).addTo(compositeDisposable)
    }

    class MovieDetailsViewModelFactory(
        private val repository: MovieRepository
        ) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>) =
            MovieDetailsViewModel(repository) as T
    }
}
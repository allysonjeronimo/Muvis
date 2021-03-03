package com.allysonjeronimo.muvis.ui.movielist

import androidx.lifecycle.*
import com.allysonjeronimo.muvis.model.db.entity.Movie
import com.allysonjeronimo.muvis.repository.MovieRepository
import kotlinx.coroutines.launch
import com.allysonjeronimo.muvis.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieListViewModel(
    private val repository:MovieRepository
) : ViewModel(){

    private val compositeDisposable = CompositeDisposable()

    private val _moviesLiveData = MutableLiveData<List<Movie>>()
    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    private val _errorOnLoadingLiveData = MutableLiveData<Int>()

    val moviesLiveData:LiveData<List<Movie>>
        get() = _moviesLiveData

    val isLoadingLiveData:LiveData<Boolean>
        get() = _isLoadingLiveData

    val errorOnLoadingLiveData:LiveData<Int>
        get() = _errorOnLoadingLiveData

    private fun getMovies(favorite:Boolean) =
        if(!favorite) repository.getMovies(compositeDisposable) else repository.getFavoriteMovies()

    fun loadMovies(favorite: Boolean = false){
        try{
            _isLoadingLiveData.value = true
            compositeDisposable.add(
                getMovies(favorite)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _moviesLiveData.postValue(it)
                        },
                        {
                            it.printStackTrace()
                            _errorOnLoadingLiveData.value = R.string.movie_list_error_on_loading
                        }
                    )
            )
            _isLoadingLiveData.value = false
        }catch(ex:Exception){
            ex.printStackTrace()
            _isLoadingLiveData.value = false
            _errorOnLoadingLiveData.value = R.string.movie_list_error_on_loading
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    class MovieListViewModelFactory(
        private val repository: MovieRepository
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>) =
            MovieListViewModel(repository) as T
    }
}
package com.allysonjeronimo.muvis.ui.movielist

import androidx.lifecycle.*
import com.allysonjeronimo.muvis.model.db.entity.Movie
import com.allysonjeronimo.muvis.repository.MovieRepository
import kotlinx.coroutines.launch
import com.allysonjeronimo.muvis.R

class MovieListViewModel(
    private val repository:MovieRepository
) : ViewModel() {

    private val _moviesLiveData = MutableLiveData<List<Movie>>()
    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    private val _errorOnLoadingLiveData = MutableLiveData<Int>()

    val moviesLiveData:LiveData<List<Movie>>
        get() = _moviesLiveData

    val isLoadingLiveData:LiveData<Boolean>
        get() = _isLoadingLiveData

    val errorOnLoadingLiveData:LiveData<Int>
        get() = _errorOnLoadingLiveData

    fun loadMovies(favorites:Boolean = false, refresh:Boolean = false){
        viewModelScope.launch {
            try{
                _isLoadingLiveData.value = true
                _moviesLiveData.value =
                    if(!favorites)
                        repository.getMovies(refresh)
                    else
                        repository.getFavoriteMovies()
                _isLoadingLiveData.value = false
            }catch(ex:Exception){
                _isLoadingLiveData.value = false
                _errorOnLoadingLiveData.value = R.string.movie_list_error_on_loading
            }
        }
    }

    class MovieListViewModelFactory(
        private val repository: MovieRepository
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>) =
            MovieListViewModel(repository) as T
    }
}
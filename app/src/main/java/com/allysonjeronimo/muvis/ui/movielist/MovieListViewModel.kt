package com.allysonjeronimo.muvis.ui.movielist

import androidx.lifecycle.*
import com.allysonjeronimo.muvis.model.db.entity.Movie
import com.allysonjeronimo.muvis.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val repository:MovieRepository
) : ViewModel() {

    private val moviesLiveData = MutableLiveData<List<Movie>>()

    fun moviesLiveData() = moviesLiveData as LiveData<List<Movie>>

    fun loadMovies(){
        viewModelScope.launch {
            val movies = repository.movies()
            moviesLiveData.value = movies
        }
    }

    class MovieListViewModelFactory(
        private val repository: MovieRepository
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MovieListViewModel(repository) as T
        }
    }
}
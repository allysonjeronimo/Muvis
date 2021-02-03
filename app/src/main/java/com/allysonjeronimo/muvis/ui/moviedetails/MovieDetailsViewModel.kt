package com.allysonjeronimo.muvis.ui.moviedetails

import androidx.lifecycle.*
import com.allysonjeronimo.muvis.R
import com.allysonjeronimo.muvis.model.db.entity.Movie
import com.allysonjeronimo.muvis.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movieLiveData = MutableLiveData<Movie>()
    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    private val _errorOnLoadingLiveData = MutableLiveData<Int>()

    val movieLiveData: LiveData<Movie>
        get() = _movieLiveData

    val isLoadingLiveData:LiveData<Boolean>
        get() = _isLoadingLiveData

    val errorLiveData:LiveData<Int>
        get() = _errorOnLoadingLiveData

    fun loadMovieDetails(id:Int){
        viewModelScope.launch {
            try{
                _isLoadingLiveData.value = true
                val movie = repository.getDetails(id)
                _movieLiveData.value = movie
                _isLoadingLiveData.value = false
            }catch(ex:Exception){
                _isLoadingLiveData.value = false
                _errorOnLoadingLiveData.value = R.string.movie_details_error_on_loading
            }
        }
    }

    class MovieDetailsViewModelFactory(
        private val repository: MovieRepository
        ) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>) =
            MovieDetailsViewModel(repository) as T
    }
}
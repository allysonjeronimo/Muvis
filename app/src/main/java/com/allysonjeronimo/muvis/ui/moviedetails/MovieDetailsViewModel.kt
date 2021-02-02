package com.allysonjeronimo.muvis.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.allysonjeronimo.muvis.repository.MovieRepository

class MovieDetailsViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    class MovieDetailsViewModelFactory(
        private val repository: MovieRepository
        ) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>) =
            MovieDetailsViewModel(repository) as T
    }
}
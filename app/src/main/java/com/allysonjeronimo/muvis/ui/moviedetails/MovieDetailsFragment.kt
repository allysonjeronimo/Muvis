package com.allysonjeronimo.muvis.ui.moviedetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.allysonjeronimo.muvis.R
import com.allysonjeronimo.muvis.model.network.MovieDBApi
import com.allysonjeronimo.muvis.repository.MovieDataRepository
import com.allysonjeronimo.muvis.repository.MovieRepository

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {

    private lateinit var viewModel: MovieDetailsViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createViewModel()
        observeEvents()
    }

    private fun createViewModel() {
        val api = MovieDBApi.getService()
        val repository = MovieDataRepository(api)

        viewModel = ViewModelProvider(
            this,
            MovieDetailsViewModel.MovieDetailsViewModelFactory(repository)
        ).get(MovieDetailsViewModel::class.java)
    }

    private fun observeEvents() {

    }

    override fun onStart() {
        super.onStart()
    }

}
package com.allysonjeronimo.muvis.ui.moviedetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.allysonjeronimo.muvis.R
import com.allysonjeronimo.muvis.extensions.load
import com.allysonjeronimo.muvis.model.db.entity.Movie
import com.allysonjeronimo.muvis.model.network.MovieDBApi
import com.allysonjeronimo.muvis.repository.MovieDataRepository
import com.allysonjeronimo.muvis.repository.MovieRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.movie_details_fragment.*
import kotlinx.android.synthetic.main.movie_details_fragment.progress
import kotlinx.android.synthetic.main.movie_list_fragment.*

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {

    private lateinit var viewModel: MovieDetailsViewModel
    private val args:MovieDetailsFragmentArgs by navArgs()

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
        viewModel.movieLiveData.observe(this.viewLifecycleOwner, {
            movie ->
            updateViewsVisibility(true)
            showDetails(movie)
        })
        viewModel.isLoadingLiveData.observe(this.viewLifecycleOwner, {
            isLoading ->
            updateViewsVisibility(!isLoading)
            updateProgressVisibility(isLoading)
        })
        viewModel.errorLiveData.observe(this.viewLifecycleOwner, {
            stringResource -> showMessage(stringResource)
        })
    }

    private fun showDetails(movie: Movie) {
        movie.backdropPath?.let{
            image_backdrop.load("https://image.tmdb.org/t/p/original/$it")
        }
        text_title.text = movie.title
        text_overview_content.text = movie.overview
    }

    private fun updateProgressVisibility(isLoading: Boolean) {
        progress.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    private fun updateViewsVisibility(visible:Boolean){
        group_views.visibility = if(visible) View.VISIBLE else View.GONE
    }

    private fun showMessage(stringResource:Int){
        Snackbar.make(
            requireView(),
            stringResource,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onStart() {
        super.onStart()
        updateViewsVisibility(false)
        viewModel.loadMovieDetails(args.movieId)
    }

}
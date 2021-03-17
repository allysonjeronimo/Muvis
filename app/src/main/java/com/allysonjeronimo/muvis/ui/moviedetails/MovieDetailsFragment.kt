package com.allysonjeronimo.muvis.ui.moviedetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import com.allysonjeronimo.muvis.R
import com.allysonjeronimo.muvis.di.DaggerAppComponent
import com.allysonjeronimo.muvis.di.DataModule
import com.allysonjeronimo.muvis.extensions.load
import com.allysonjeronimo.muvis.extensions.setIcon
import com.allysonjeronimo.muvis.model.db.AppDatabase
import com.allysonjeronimo.muvis.model.db.entity.Movie
import com.allysonjeronimo.muvis.model.network.MovieDBClient
import com.allysonjeronimo.muvis.repository.MovieDataSource
import com.allysonjeronimo.muvis.repository.MovieRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.movie_details_fragment.*
import kotlinx.android.synthetic.main.movie_details_fragment.progress
import javax.inject.Inject

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {

    private lateinit var viewModel: MovieDetailsViewModel
    private val args:MovieDetailsFragmentArgs by navArgs()
    private lateinit var movie:Movie
    @Inject
    lateinit var repository: MovieRepository

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupComponent()
        createViewModel()
        observeEvents()
        setListeners()
    }

    private fun setupComponent() {
        val component = DaggerAppComponent
            .builder()
            .dataModule(DataModule(requireContext()))
            .build()

        component.inject(this)
    }

    private fun createViewModel() {
        viewModel = ViewModelProvider(
            this,
            MovieDetailsViewModel.MovieDetailsViewModelFactory(repository)
        ).get(MovieDetailsViewModel::class.java)
    }

    private fun observeEvents() {
        viewModel.movieLiveData.observe(this.viewLifecycleOwner, {
            movie ->
            this.movie = movie
            updateViewsVisibility(true)
            showDetails()
        })
        viewModel.isLoadingLiveData.observe(this.viewLifecycleOwner, {
            isLoading ->
            updateViewsVisibility(!isLoading)
            updateProgressVisibility(isLoading)
        })
        viewModel.errorOnLoadingLiveData.observe(this.viewLifecycleOwner, {
            stringResource -> showMessage(stringResource)
        })
        viewModel.errorOnUpdateLiveData.observe(this.viewLifecycleOwner, {
            stringResource -> showMessage(stringResource)
        })
    }

    private fun showDetails() {
        movie.backdropPath?.let{
            image_backdrop.load("https://image.tmdb.org/t/p/original/$it")
        }
        text_title.text = movie.title
        text_overview_content.text = movie.overview

        fab_favorite.setIcon(
            if(movie.isFavorite)
                R.drawable.ic_favorite
            else
                R.drawable.ic_favorite_border
        )
    }

    private fun updateProgressVisibility(isLoading: Boolean) {
        progress.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    private fun updateViewsVisibility(visible:Boolean){
        view_details.visibility = if(visible) View.VISIBLE else View.GONE
    }

    private fun showMessage(stringResource:Int){
        Snackbar.make(
            requireView(),
            stringResource,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun setListeners() {
        fab_favorite.setOnClickListener {
            updateFavoriteStatus()
        }
    }

    private fun updateFavoriteStatus(){
        viewModel.togglesFavorite(movie)
    }

    override fun onStart() {
        super.onStart()
        updateViewsVisibility(false)
        viewModel.loadMovieDetails(args.movieId)
    }

}
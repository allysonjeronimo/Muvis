package com.allysonjeronimo.muvis.ui.movielist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.allysonjeronimo.muvis.R
import com.allysonjeronimo.muvis.model.db.entity.Movie
import com.allysonjeronimo.muvis.model.network.MovieDBApi
import com.allysonjeronimo.muvis.repository.MovieDataRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.movie_list_fragment.*

class MovieListFragment : Fragment(R.layout.movie_list_fragment) {

    private lateinit var viewModel: MovieListViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createViewModel()
        observeEvents()
    }

    private fun createViewModel(){
        val api = MovieDBApi.getService()

        val repository = MovieDataRepository(api)

        viewModel = ViewModelProvider(
            this,
            MovieListViewModel.MovieListViewModelFactory(repository))
            .get(MovieListViewModel::class.java)
    }

    private fun observeEvents() {
        viewModel.moviesLiveData.observe(this.viewLifecycleOwner, {
            movies ->
            updateRecyclerViewVisibility(true)
            recycler_movies.adapter = MovieListAdapter(movies, this::onClickMovie)
        })
        viewModel.isLoadingLiveData.observe(this.viewLifecycleOwner, {
            isLoading ->
            updateProgressVisibility(isLoading)
            updateRecyclerViewVisibility(!isLoading)
        })
        viewModel.errorOnLoadingLiveData.observe(this.viewLifecycleOwner, {
            stringResource -> showMessage(stringResource)
        })
    }

    private fun updateProgressVisibility(isLoading: Boolean) {
        progress.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    private fun updateRecyclerViewVisibility(visible:Boolean){
        recycler_movies.visibility = if(visible) View.VISIBLE else View.GONE
    }

    private fun showMessage(stringResource:Int){
        Snackbar.make(
            requireView(),
            stringResource,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun onClickMovie(movie: Movie){
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment()
        findNavController().navigate(action)
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadMovies()
    }
}
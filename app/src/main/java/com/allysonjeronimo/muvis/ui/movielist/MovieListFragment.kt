package com.allysonjeronimo.muvis.ui.movielist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.allysonjeronimo.muvis.R
import com.allysonjeronimo.muvis.model.network.API_KEY
import com.allysonjeronimo.muvis.model.network.BASE_URL
import com.allysonjeronimo.muvis.model.network.MovieDBApi
import com.allysonjeronimo.muvis.model.network.entity.MovieDBResponse
import com.allysonjeronimo.muvis.repository.MovieDataRepository
import kotlinx.android.synthetic.main.movie_list_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieListFragment : Fragment(R.layout.movie_list_fragment) {

    private lateinit var viewModel: MovieListViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createViewModel()
        observeEvents()
    }

    private fun createViewModel(){
        val api = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieDBApi::class.java)

        val repository = MovieDataRepository(api)

        viewModel = ViewModelProvider(
            this,
            MovieListViewModel.MovieListViewModelFactory(repository))
            .get(MovieListViewModel::class.java)
    }

    private fun observeEvents() {
        viewModel.moviesLiveData().observe(this.viewLifecycleOwner, {
            movies -> recycler_movies.adapter = MovieListAdapter(movies)
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadMovies()
    }

}
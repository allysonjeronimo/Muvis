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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieListFragment : Fragment(R.layout.movie_list_fragment) {

    private lateinit var viewModel: MovieListViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)

        val api = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieDBApi::class.java)


        api.movies(API_KEY).enqueue(object:Callback<MovieDBResponse>{
            override fun onResponse(
                call: Call<MovieDBResponse>,
                response: Response<MovieDBResponse>
            ) {
                if(response.isSuccessful){

                    Log.i(
                        MovieListFragment::class.simpleName,
                        response.body().toString()
                    )
                }
            }

            override fun onFailure(call: Call<MovieDBResponse>, t: Throwable) {
                Log.e(MovieListFragment::class.simpleName, t.message.toString())
            }
        })
    }

}
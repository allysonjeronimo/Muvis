package com.allysonjeronimo.muvis.repository

import com.allysonjeronimo.muvis.model.db.entity.Movie
import com.allysonjeronimo.muvis.model.network.API_KEY
import com.allysonjeronimo.muvis.model.network.MovieDBApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDataRepository(
    private val api: MovieDBApi
) : MovieRepository{

    override suspend fun getMovies(): List<Movie> {
        return withContext(Dispatchers.IO){
            api.movies(API_KEY).getMoviesAsModel()
        }
    }
}
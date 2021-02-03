package com.allysonjeronimo.muvis.repository

import com.allysonjeronimo.muvis.model.db.entity.Movie
import com.allysonjeronimo.muvis.model.network.API_KEY
import com.allysonjeronimo.muvis.model.network.MovieDBApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDataRepository(
    private val api: MovieDBApi
) : MovieRepository{

    override suspend fun getPopular(): List<Movie> {
        return withContext(Dispatchers.IO){
            api.getPopular(API_KEY).getMoviesAsEntities()
        }
    }

    override suspend fun getDetails(movieId:Int): Movie {
        return withContext(Dispatchers.IO){
            api.getDetails(movieId, API_KEY).toMovieEntity()
        }
    }
}
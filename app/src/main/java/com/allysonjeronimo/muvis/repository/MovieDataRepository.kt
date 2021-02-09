package com.allysonjeronimo.muvis.repository

import com.allysonjeronimo.muvis.model.db.dao.MovieDao
import com.allysonjeronimo.muvis.model.db.entity.Movie
import com.allysonjeronimo.muvis.model.network.API_KEY
import com.allysonjeronimo.muvis.model.network.MovieDBApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDataRepository(
    private val dao: MovieDao,
    private val api: MovieDBApi
) : MovieRepository{

    override suspend fun getMovies(): List<Movie> {
        return withContext(Dispatchers.IO){
            val movies = api.getPopular(API_KEY).getMoviesAsEntities()
            dao.insertAll(movies)
            dao.getAll()
        }
    }

    override suspend fun getFavoriteMovies(): List<Movie> {
        return withContext(Dispatchers.IO){
            dao.getAllFavorites()
        }
    }

    override suspend fun getMovie(movieId:Int): Movie {
        return withContext(Dispatchers.IO){
            dao.getById(movieId)
        }
    }

    override suspend fun update(movie: Movie) {
        withContext(Dispatchers.IO){
            dao.update(movie)
        }
    }
}
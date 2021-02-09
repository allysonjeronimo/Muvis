package com.allysonjeronimo.muvis.repository

import com.allysonjeronimo.muvis.model.db.entity.Movie

interface MovieRepository {

    suspend fun getMovies() : List<Movie>

    suspend fun getFavoriteMovies() : List<Movie>

    suspend fun getMovie(movieId:Int) : Movie

    suspend fun update(movie:Movie)
}
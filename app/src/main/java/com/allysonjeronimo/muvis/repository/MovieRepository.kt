package com.allysonjeronimo.muvis.repository

import com.allysonjeronimo.muvis.model.db.entity.Movie

interface MovieRepository {

    suspend fun getPopular() : List<Movie>

    suspend fun getDetails(movieId:Int) : Movie
}
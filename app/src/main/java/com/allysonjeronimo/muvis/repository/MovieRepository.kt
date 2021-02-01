package com.allysonjeronimo.muvis.repository

import com.allysonjeronimo.muvis.model.db.entity.Movie

interface MovieRepository {

    suspend fun movies() : List<Movie>

}
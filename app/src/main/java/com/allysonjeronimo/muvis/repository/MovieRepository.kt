package com.allysonjeronimo.muvis.repository

import com.allysonjeronimo.muvis.model.db.entity.Movie
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

interface MovieRepository {

    fun getMovies() : Single<List<Movie>>

    fun getFavoriteMovies() : Single<List<Movie>>

    fun getMovie(movieId:Int) : Single<Movie>

    fun update(movie:Movie) : Completable
}
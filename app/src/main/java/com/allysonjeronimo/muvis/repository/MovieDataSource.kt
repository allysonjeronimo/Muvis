package com.allysonjeronimo.muvis.repository

import com.allysonjeronimo.muvis.model.db.dao.MovieDao
import com.allysonjeronimo.muvis.model.db.entity.Movie
import com.allysonjeronimo.muvis.model.network.MovieDBApi
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(
    private val dao: MovieDao,
    private val api: MovieDBApi
) : MovieRepository{

    override fun getMovies(): Single<List<Movie>> =
        Single.concat(
            api.getPopular()
                .flatMap { Single.just(it.getMoviesAsEntities())}
                .doOnSuccess { dao.insertAll(it) },
            dao.getAll()
        ).firstOrError()

    override fun getFavoriteMovies() = dao.getAllFavorites()

    override fun getMovie(movieId:Int) = dao.getById(movieId)

    override fun update(movie: Movie) = dao.update(movie)

}
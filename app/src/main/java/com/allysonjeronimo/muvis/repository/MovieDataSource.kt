package com.allysonjeronimo.muvis.repository

import com.allysonjeronimo.muvis.model.db.dao.MovieDao
import com.allysonjeronimo.muvis.model.db.entity.Movie
import com.allysonjeronimo.muvis.model.network.MovieDBApi
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(
    private val dao: MovieDao,
    private val api: MovieDBApi
) : MovieRepository{

    override fun getMovies(compositeDisposable: CompositeDisposable): Single<List<Movie>> {
        return Single.create<List<Movie>>{ emitter ->
            api.getPopular()
                .map { it.getMoviesAsEntities() }
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        dao.insertAll(it)
                        emitter.onSuccess(dao.getAll())
                    },
                    {
                        emitter.onSuccess(dao.getAll())
                    },
                )
        }
    }

    override fun getFavoriteMovies(): Single<List<Movie>> {
        return dao.getAllFavorites()
    }

    override fun getMovie(movieId:Int): Single<Movie> {
        return dao.getById(movieId)
    }

    override fun update(movie: Movie){
        dao.update(movie)
    }
}
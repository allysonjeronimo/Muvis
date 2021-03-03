package com.allysonjeronimo.muvis.model.network

import com.allysonjeronimo.muvis.model.network.entity.MovieDBResponse
import com.allysonjeronimo.muvis.model.network.entity.MovieDBResponseItem
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBApi {

    @GET("/3/movie/popular")
    fun getPopular() : Single<MovieDBResponse>

    @GET("/3/movie/{movie_id}")
    fun getDetails(@Path("movie_id") movieId:Int) : Single<MovieDBResponseItem>
}
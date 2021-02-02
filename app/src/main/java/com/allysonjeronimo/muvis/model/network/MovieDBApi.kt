package com.allysonjeronimo.muvis.model.network

import com.allysonjeronimo.muvis.model.network.entity.MovieDBResponse
import com.allysonjeronimo.muvis.model.network.entity.MovieDBResponseItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBApi {

    @GET("/3/movie/popular")
    suspend fun getPopular(
        @Query("api_key") apiKey:String)
    : MovieDBResponse

    @GET("/3/movie/{movie_id}")
    suspend fun getDetails(
        @Query("api_key") apiKey:String,
        @Path("movie_id") movieId:Int)
    : MovieDBResponseItem

    companion object{

        fun getService() : MovieDBApi{

            return Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieDBApi::class.java)
        }

    }
}
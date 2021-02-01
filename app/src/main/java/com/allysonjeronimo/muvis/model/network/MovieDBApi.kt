package com.allysonjeronimo.muvis.model.network

import com.allysonjeronimo.muvis.model.network.entity.MovieDBResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDBApi {

    @GET("/3/movie/popular")
    suspend fun movies(@Query("api_key") apiKey:String) : MovieDBResponse
}
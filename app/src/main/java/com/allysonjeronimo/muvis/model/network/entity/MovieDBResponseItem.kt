package com.allysonjeronimo.muvis.model.network.entity

import com.allysonjeronimo.muvis.model.db.entity.Movie
import com.google.gson.annotations.SerializedName

data class MovieDBResponseItem(
    var id: Int,
    var title: String,
    var overview:String?,
    @SerializedName("poster_path")
    var posterPath:String?,
    @SerializedName("backdrop_path")
    var backdropPath:String?,
    @SerializedName("release_date")
    var releasedate:String?
){

    fun toMovieEntity() : Movie {
        return Movie(
            id,
            title,
            overview,
            posterPath,
            backdropPath,
            releasedate
        )
    }
}
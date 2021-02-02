package com.allysonjeronimo.muvis.model.network.entity

import com.allysonjeronimo.muvis.model.db.entity.Movie
import com.google.gson.annotations.SerializedName

data class MovieDBResponseItem(
    var id: Int,
    var title: String,
    @SerializedName("poster_path")
    var posterPath:String?
){

    fun toMovieEntity() : Movie {
        return Movie(
            id,
            title,
            posterPath
        )
    }
}
package com.allysonjeronimo.muvis.model.network.entity

import com.allysonjeronimo.muvis.model.db.entity.Movie
import com.google.gson.annotations.SerializedName

data class MovieDBResponse(
  var page:Int,
  @SerializedName("total_results")
  var totalResults:Int,
  @SerializedName("total_pages")
  var totalPages:Int,
  var results:List<MovieDBResponseItem>
){

    fun getMoviesAsEntities() : List<Movie>{
        return results.map{
            movieResponse -> movieResponse.toMovieEntity()
        }
    }
}
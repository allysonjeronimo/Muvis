package com.allysonjeronimo.muvis.model.network.entity

import com.google.gson.annotations.SerializedName

data class MovieDBResponseItem(
    var id: Int,
    var title: String,
    @SerializedName("poster_path")
    var posterPath:String?
)
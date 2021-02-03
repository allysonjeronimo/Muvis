package com.allysonjeronimo.muvis.model.db.entity

data class Movie(
    var id:Int,
    var title:String,
    var overview:String?,
    var posterPath:String?,
    var backdropPath:String?
)
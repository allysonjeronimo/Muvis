package com.allysonjeronimo.muvis.model.db.entity

data class Movie(
    var id:Int,
    var title:String,
    var posterPath:String?
){
    override fun equals(other: Any?): Boolean {
        if(other is Movie)
            return id == other.id
        return false
    }
}
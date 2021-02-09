package com.allysonjeronimo.muvis.model.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey
    var id:Int,
    var title:String,
    var overview:String?,
    var posterPath:String?,
    var backdropPath:String?,
    var releaseDate: String?,
    var isFavorite:Boolean = false
){

    fun togglesFavorite(){
        this.isFavorite = !this.isFavorite
    }
}
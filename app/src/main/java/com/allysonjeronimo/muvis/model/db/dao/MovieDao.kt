package com.allysonjeronimo.muvis.model.db.dao

import androidx.room.*
import com.allysonjeronimo.muvis.model.db.entity.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(movies:List<Movie>)

    @Update
    suspend fun update(movie:Movie)

    @Query("SELECT * FROM Movie WHERE id = :id")
    suspend fun getById(id:Int) : Movie

    @Query("SELECT * FROM Movie")
    suspend fun getAll() : List<Movie>

    @Query("SELECT * FROM Movie WHERE isFavorite = 1")
    suspend fun getAllFavorites() : List<Movie>

}
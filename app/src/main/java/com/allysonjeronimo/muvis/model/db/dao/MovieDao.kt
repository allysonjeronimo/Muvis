package com.allysonjeronimo.muvis.model.db.dao

import androidx.room.*
import com.allysonjeronimo.muvis.model.db.entity.Movie
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(movies:List<Movie>)

    @Update
    fun update(movie:Movie)

    @Query("SELECT * FROM Movie WHERE id = :id")
    fun getById(id:Int) : Single<Movie>

    @Query("SELECT * FROM Movie")
    fun getAll() : Single<List<Movie>>

    @Query("SELECT * FROM Movie WHERE isFavorite = 1")
    fun getAllFavorites() : Single<List<Movie>>

}
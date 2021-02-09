package com.allysonjeronimo.muvis.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.allysonjeronimo.muvis.model.db.dao.MovieDao
import com.allysonjeronimo.muvis.model.db.entity.Movie

@Database(
    entities = [Movie::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){

    abstract fun movieDao() : MovieDao

    companion object{
        @Volatile
        private var INSTANCE:AppDatabase? = null
        private const val DB_NAME = "muvis-db"

        fun getInstance(context: Context) : AppDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DB_NAME
                    ).build()
                }
                return instance
            }
        }
    }
}
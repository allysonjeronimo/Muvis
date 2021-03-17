package com.allysonjeronimo.muvis.di

import android.content.Context
import com.allysonjeronimo.muvis.model.db.AppDatabase
import com.allysonjeronimo.muvis.model.db.dao.MovieDao
import com.allysonjeronimo.muvis.model.network.MovieDBApi
import com.allysonjeronimo.muvis.model.network.MovieDBClient
import com.allysonjeronimo.muvis.repository.MovieDataSource
import com.allysonjeronimo.muvis.repository.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule(private val context: Context) {

    @Singleton
    @Provides
    fun providesApi() : MovieDBApi{
        return MovieDBClient.getMovieDBApi()
    }

    @Singleton
    @Provides
    fun providesDao() : MovieDao {
        return AppDatabase.getInstance(context).movieDao()
    }

    @Singleton
    @Provides
    fun providesRepository(dao: MovieDao, api: MovieDBApi) : MovieRepository{
        return MovieDataSource(dao, api)
    }
}
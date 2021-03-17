package com.allysonjeronimo.muvis.di

import android.app.Application
import android.content.Context
import com.allysonjeronimo.muvis.ui.moviedetails.MovieDetailsFragment
import com.allysonjeronimo.muvis.ui.movielist.MovieListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface AppComponent {

    fun inject(fragment: MovieListFragment)

    fun inject(fragment: MovieDetailsFragment)
}
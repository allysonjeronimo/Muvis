package com.allysonjeronimo.muvis.ui.movielist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.allysonjeronimo.muvis.R
import com.allysonjeronimo.muvis.model.db.entity.Movie
import com.allysonjeronimo.muvis.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieListViewModelTest{

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val repository = mockk<MovieRepository>()
    private val moviesLiveDataObserver = mockk<Observer<List<Movie>>>(relaxed = true)
    private val errorOnLoadingLiveDataObserver  = mockk<Observer<Int>>(relaxed = true)

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanUp(){
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when view model loadMovies gets success then sets moviesLiveData`(){

        val viewModel = instantiateViewModel()

        val mockedList = listOf(
            Movie(1, "Movie 1", ""),
            Movie(2, "Movie 2", ""),
            Movie(3, "Movie 3", "")
        )

        coEvery {
            repository.getMovies()
        } returns mockedList

        viewModel.loadMovies()

        coVerify {
            repository.getMovies()
        }
        coVerify {
            moviesLiveDataObserver.onChanged(mockedList)
        }
    }

    @Test
    fun `when view model loadMovies gets exception then sets errorOnLoadingLiveData`(){

        val viewModel = instantiateViewModel()

        coEvery {
            repository.getMovies()
        } throws Exception()

        viewModel.loadMovies()

        coVerify {
            repository.getMovies()
        }
        coVerify {
            errorOnLoadingLiveDataObserver.onChanged(R.string.movie_list_error_on_loading)
        }
    }

    private fun instantiateViewModel() : MovieListViewModel {
        val viewModel = MovieListViewModel(repository)
        viewModel.moviesLiveData.observeForever(moviesLiveDataObserver)
        viewModel.errorOnLoadingLiveData.observeForever(errorOnLoadingLiveDataObserver)
        return viewModel
    }
}
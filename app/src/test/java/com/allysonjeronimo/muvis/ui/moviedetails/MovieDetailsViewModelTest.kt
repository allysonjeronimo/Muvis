package com.allysonjeronimo.muvis.ui.moviedetails

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
class MovieDetailsViewModelTest{

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val repository = mockk<MovieRepository>()
    private val movieLiveDataObserver = mockk<Observer<Movie>>(relaxed = true)
    private val errorOnLoadingLiveDataObserver = mockk<Observer<Int>>(relaxed=true)

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanUp(){
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    private fun instantiateViewModel() : MovieDetailsViewModel{
        val viewModel = MovieDetailsViewModel(repository)
        viewModel.movieLiveData.observeForever(movieLiveDataObserver)
        viewModel.errorLiveData.observeForever(errorOnLoadingLiveDataObserver)
        return viewModel
    }

    @Test
    fun `when view model loadMovieDetails gets success then sets movieLiveData`(){
        val viewModel = instantiateViewModel()

        val mockedMovie = Movie(1, "Title 1", "")

        coEvery {
            repository.getDetails(1)
        } returns mockedMovie

        viewModel.loadMovieDetails(1)

        coVerify {
            repository.getDetails(1)
        }

        coVerify {
            movieLiveDataObserver.onChanged(mockedMovie)
        }
    }

    @Test
    fun `when view model loadMovieDetails gets exception then sets errorOnLoadingLiveData`(){
        val viewModel = instantiateViewModel()

        coEvery {
            repository.getDetails(1)
        } throws Exception()

        viewModel.loadMovieDetails(1)

        coVerify {
            repository.getDetails(1)
        }

        coVerify {
            errorOnLoadingLiveDataObserver.onChanged(R.string.movie_details_error_on_loading)
        }
    }
}
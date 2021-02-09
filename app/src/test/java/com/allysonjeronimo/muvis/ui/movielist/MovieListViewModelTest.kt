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

    private lateinit var viewModel:MovieListViewModel
    private lateinit var mockedList:List<Movie>
    private lateinit var mockedFavoritesList:List<Movie>

    private val repository = mockk<MovieRepository>()
    private val moviesLiveDataObserver = mockk<Observer<List<Movie>>>(relaxed = true)
    private val errorOnLoadingLiveDataObserver  = mockk<Observer<Int>>(relaxed = true)

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
        instantiateViewModel()
        mockLists()
    }

    @After
    fun cleanUp(){
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    private fun instantiateViewModel(){
        viewModel = MovieListViewModel(repository)
        viewModel.moviesLiveData.observeForever(moviesLiveDataObserver)
        viewModel.errorOnLoadingLiveData.observeForever(errorOnLoadingLiveDataObserver)
    }

    private fun mockLists(){
        mockedList = listOf(
            Movie(1, "Movie 1", "Overview 1", "", "", "", false),
            Movie(2, "Movie 2", "Overview 2", "", "", "", false),
            Movie(3, "Movie 3", "Overview 3", "", "", "", true),
            Movie(4, "Movie 4", "Overview 4", "", "", "", true)
        )
        mockedFavoritesList = mockedList.filter { it.isFavorite }
    }

    @Test
    fun `when view model loadMovies gets success then sets moviesLiveData`(){

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
    fun `when view model loadMovies with favorites param then sets moviesLiveData with favorite movies`(){

        coEvery {
            repository.getFavoriteMovies()
        } returns mockedFavoritesList

        viewModel.loadMovies(true)

        coVerify {
            repository.getFavoriteMovies()
        }
        coVerify {
            moviesLiveDataObserver.onChanged(mockedFavoritesList)
        }
    }

    @Test
    fun `when view model loadMovies gets exception then sets errorOnLoadingLiveData`(){

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

}
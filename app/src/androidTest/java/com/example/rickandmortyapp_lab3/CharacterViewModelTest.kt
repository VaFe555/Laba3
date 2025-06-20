package com.example.rickandmortyapp_lab3

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class CharacterViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CharacterViewModel
    private lateinit var apiService: RickAndMortyApi

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        apiService = mock(RickAndMortyApi::class.java)
        // TODO: inject apiService into viewModel
        viewModel = CharacterViewModel()
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadCharacters success`() = runBlocking {
        // Arrange
        val mockResponse = CharacterResponse(Info(1, 1, null, null), listOf(Character(1, "Rick Sanchez", "Alive", "Human", "", "Male", Origin("Earth", ""), Location("Earth", ""), "image.url", listOf(), "", "")))
        `when`(apiService.getCharacters()).thenReturn(retrofit2.Response.success(mockResponse))

        val observer = mock(Observer::class.java) as Observer<List<Character>>
        viewModel.characters.observeForever(observer)

        viewModel.loadCharacters()

        verify(apiService, times(1)).getCharacters()
        verify(observer).onChanged(mockResponse.results)

        viewModel.characters.removeObserver(observer)
    }

    @Test
    fun `loadCharacters failure`() = runBlocking {
        // Arrange
        val errorMessage = "Error message"
        `when`(apiService.getCharacters()).thenReturn(retrofit2.Response.error(500, okhttp3.ResponseBody.create(okhttp3.MediaType.parse("application/json"), "")))

        val observer = mock(Observer::class.java) as Observer<String>
        viewModel.errorMessage.observeForever(observer)

        viewModel.loadCharacters()

        verify(apiService, times(1)).getCharacters()

        val argument = argumentCaptor<String>()
        verify(observer).onChanged(argument.capture())
        assert(argument.firstValue.contains("Error loading characters"))

        viewModel.errorMessage.removeObserver(observer)
    }
}
package com.example.rickandmortyapp_lab3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class CharacterViewModel : ViewModel() {

    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> = _characters

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response: Response<CharacterResponse> = RetrofitClient.instance.getCharacters()
                if (response.isSuccessful) {
                    _characters.value = response.body()?.results ?: emptyList()
                } else {
                    _errorMessage.value = "Error loading characters: ${response.code()} - ${response.message()}"
                }
            } catch (e: IOException) {
                _errorMessage.value = "Network error: ${e.message}"
            } catch (e: Exception) {
                _errorMessage.value = "Unexpected error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
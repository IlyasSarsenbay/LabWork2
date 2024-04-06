package com.example.dogs.model

sealed class DogListState {
    data class Success(val items: List<Dog>) : DogListState()
    data class Loading(val isLoading: Boolean) : DogListState()
    data class Error(val message: String = "Unknown error occurred") : DogListState()
}
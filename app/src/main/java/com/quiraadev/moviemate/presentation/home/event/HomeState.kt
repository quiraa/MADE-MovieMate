package com.quiraadev.moviemate.presentation.home.event

sealed class HomeState {
	data object Loading: HomeState()
	data object Success: HomeState()
	data class Error(val message: String) : HomeState()
}
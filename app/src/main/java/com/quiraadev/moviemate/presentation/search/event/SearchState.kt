package com.quiraadev.moviemate.presentation.search.event

sealed class SearchState {
	data class Error(val message: String): SearchState()
	data object Loading: SearchState()
	data object EmptyQuery: SearchState()
	data object Success: SearchState()
}
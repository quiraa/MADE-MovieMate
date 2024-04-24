package com.quiraadev.moviemate.presentation.home.event

sealed class HomeEvent {
	data object GetMovieListEvent : HomeEvent()
	data class ChangeCategoryEvent(val category: MovieCategory) : HomeEvent()
}

enum class MovieCategory(val categoryValue: String) {
	POPULAR("popular"),
	UPCOMING("upcoming"),
	TOP_RATED("top_rated")
}
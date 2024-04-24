package com.quiraadev.moviemate.presentation.detail.event

sealed class DetailState {
	data object Loading: DetailState()
	data object Success: DetailState()
	data class Error(val message: String) : DetailState()
}
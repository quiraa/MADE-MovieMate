package com.quiraadev.moviemate.presentation.detail.event

import com.quiraadev.core.domain.model.MovieModel

sealed class DetailEvent {
	data class GetMovieRecommendationEvent(val movieId: Int) : DetailEvent()
	data class SetMovieBookmarkEvent(val movie: MovieModel, val newStatus: Boolean) : DetailEvent()
}
package com.quiraadev.moviemate.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.quiraadev.core.data.Resource
import com.quiraadev.core.domain.model.MovieModel
import com.quiraadev.core.domain.usecase.MovieMateUseCase
import com.quiraadev.moviemate.presentation.detail.event.DetailEvent
import com.quiraadev.moviemate.presentation.detail.event.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
	private val movieMateUseCase: MovieMateUseCase
): ViewModel() {


	private val _uiState = MutableStateFlow<DetailState>(DetailState.Loading)
	val uiState = _uiState.asLiveData()

	private val _recommendedMovies = MutableStateFlow<List<MovieModel>>(emptyList())
	val recommendedMovies = _recommendedMovies.asLiveData()


	fun onEvent(event: DetailEvent) {
		when(event) {
			is DetailEvent.GetMovieRecommendationEvent -> {
				viewModelScope.launch {
					getMovieRecommendation(event.movieId)
				}
			}
			is DetailEvent.SetMovieBookmarkEvent -> {
				viewModelScope.launch {
					setBookmark(event.movie, event.newStatus)
				}
			}
		}
	}

	private suspend fun getMovieRecommendation(movieId: Int) {
		movieMateUseCase.getMovieRecommendation(movieId).collectLatest { state ->
			when(state) {
				is Resource.Error -> {
					_uiState.emit(DetailState.Error(state.message ?: "Error"))
				}
				is Resource.Loading -> {
					_uiState.emit(DetailState.Loading)
				}
				is Resource.Success -> {
					_uiState.emit(DetailState.Success)
					_recommendedMovies.value = state.data ?: emptyList()
				}
			}
		}
	}

	private fun setBookmark(movie: MovieModel, newStatus: Boolean) {
		return movieMateUseCase.setBookmarkedMovie(movie, newStatus)
	}
}
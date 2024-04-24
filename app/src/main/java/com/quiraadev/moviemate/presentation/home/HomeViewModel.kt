package com.quiraadev.moviemate.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.quiraadev.core.data.Resource
import com.quiraadev.core.domain.model.MovieModel
import com.quiraadev.core.domain.usecase.MovieMateUseCase
import com.quiraadev.moviemate.presentation.home.event.HomeEvent
import com.quiraadev.moviemate.presentation.home.event.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	private val movieMateUseCase: MovieMateUseCase
) : ViewModel() {
	private val _uiState = MutableStateFlow<HomeState>(HomeState.Loading)
	val uiState = _uiState.asLiveData()

	private val _movieList = MutableStateFlow<List<MovieModel>>(emptyList())
	val movieList = _movieList.asLiveData()

	init {
		onEvent(HomeEvent.GetMovieListEvent)
	}

	fun onEvent(event: HomeEvent) {
		when (event) {
			is HomeEvent.GetMovieListEvent -> {
				viewModelScope.launch {
					getMovies()
				}
			}
			is HomeEvent.ChangeCategoryEvent -> {
				val movieType = event.category.categoryValue
				viewModelScope.launch {
					getMovies(movieType)
				}
			}
		}
	}

	private suspend fun getMovies(movieType: String = "popular") {
		movieMateUseCase.getAllMovies(movieType).collectLatest { status ->
			when(status) {
				is Resource.Error -> _uiState.emit(HomeState.Error(status.message ?: "Error"))
				is Resource.Loading -> _uiState.emit(HomeState.Loading)
				is Resource.Success -> {
					_uiState.emit(HomeState.Success)
					_movieList.value = status.data ?: emptyList()
				}
			}
		}
	}
}
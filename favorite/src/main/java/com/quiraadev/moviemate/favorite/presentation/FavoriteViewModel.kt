package com.quiraadev.moviemate.favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.quiraadev.core.domain.model.MovieModel
import com.quiraadev.core.domain.usecase.MovieMateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoriteViewModel (private val movieMateUseCase: MovieMateUseCase) : ViewModel() {

	private val _favoriteList = MutableStateFlow<List<MovieModel>>(emptyList())
	val favoriteList = _favoriteList.asLiveData()

	private fun getAllFavorites() {
		viewModelScope.launch {
			movieMateUseCase.getBookmarkedMovies().collectLatest { list ->
				_favoriteList.emit(list)
			}
		}
	}

	init {
		getAllFavorites()
	}
}
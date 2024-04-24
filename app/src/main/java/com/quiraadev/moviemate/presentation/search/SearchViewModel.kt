package com.quiraadev.moviemate.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.quiraadev.core.data.Resource
import com.quiraadev.core.domain.model.MovieModel
import com.quiraadev.core.domain.usecase.MovieMateUseCase
import com.quiraadev.moviemate.presentation.search.event.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
	private val movieMateUseCase: MovieMateUseCase
) : ViewModel() {
	private val queryText = MutableStateFlow("")

	private val _uiState = MutableStateFlow<SearchState>(SearchState.EmptyQuery)
	val uiState = _uiState.asLiveData()

	private val _searchedMovies = MutableStateFlow<List<MovieModel>>(emptyList())
	val searchedMovies = _searchedMovies.asLiveData()

	init {
		queryText
			.debounce(750)
			.filter {
				it.trim().isNotEmpty()
			}
			.onEach {
				_uiState.emit(SearchState.Loading)
			}
			.flatMapLatest { query ->
				movieMateUseCase.searchMovie(query)
			}
			.onEach { result ->
				when(result) {
					is Resource.Error -> _uiState.emit(SearchState.Error(result.message ?: "Error Occurred"))
					is Resource.Loading -> _uiState.emit(SearchState.Loading)
					is Resource.Success -> {
						_uiState.emit(SearchState.Success)
						_searchedMovies.value = result.data ?: emptyList()
					}
				}
			}
			.catch { exception ->
				_uiState.emit(SearchState.Error(exception.message ?: "Error on Exception"))
			}
			.launchIn(viewModelScope)
	}

	fun onQueryTextChanged(query: String) {
		queryText.value = query
	}
}
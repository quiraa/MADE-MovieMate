package com.quiraadev.core.domain.usecase

import com.quiraadev.core.data.Resource
import com.quiraadev.core.domain.model.MovieModel
import com.quiraadev.core.domain.repository.MovieMateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieMateInteractor @Inject constructor(
	private val repository: MovieMateRepository
) : MovieMateUseCase {

	override fun getAllMovies(type: String): Flow<Resource<List<MovieModel>>> {
		return repository.getAllMovies(type)
	}

	override fun getMovieRecommendation(movieId: Int): Flow<Resource<List<MovieModel>>> {
		return repository.getMovieRecommendation(movieId)
	}

	override fun searchMovie(query: String): Flow<Resource<List<MovieModel>>> {
		return repository.searchMovie(query)
	}

	override fun getBookmarkedMovies(): Flow<List<MovieModel>> {
		return repository.getBookmarkedMovies()
	}

	override fun setBookmarkedMovie(movieModel: MovieModel, bookmarked: Boolean) {
		return repository.setBookmarkedMovie(movieModel, bookmarked)
	}

}
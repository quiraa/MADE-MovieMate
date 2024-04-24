package com.quiraadev.core.domain.repository

import com.quiraadev.core.data.Resource
import com.quiraadev.core.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieMateRepository {

	fun getAllMovies(type: String) : Flow<Resource<List<MovieModel>>>

	fun getMovieRecommendation(movieId: Int) : Flow<Resource<List<MovieModel>>>

	fun searchMovie(query: String) : Flow<Resource<List<MovieModel>>>

	fun getBookmarkedMovies() : Flow<List<MovieModel>>

	fun setBookmarkedMovie(movieModel: MovieModel, bookmarked: Boolean)

}
package com.quiraadev.core.data.source.local

import com.quiraadev.core.data.source.local.entity.MovieEntity
import com.quiraadev.core.data.source.local.room.MovieMateDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
	private val dao: MovieMateDao
) {

	fun getMovieList(type: String) : Flow<List<MovieEntity>> = dao.getAllMovie(type)

	fun getBookmarkedMovieList() : Flow<List<MovieEntity>> = dao.getBookmarkedMovies()

	suspend fun insertAllMovie(movieList : List<MovieEntity>) = dao.insertAllMovie(movieList)

	fun updateBookmarkedMovie(movieEntity: MovieEntity, newState: Boolean) {
		movieEntity.isFavorite = newState
		dao.updateBookmarkedMovie(movieEntity)
	}
}
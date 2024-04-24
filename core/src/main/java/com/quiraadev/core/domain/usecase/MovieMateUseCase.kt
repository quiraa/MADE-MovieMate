package com.quiraadev.core.domain.usecase

import com.quiraadev.core.data.Resource
import com.quiraadev.core.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieMateUseCase {

	fun getAllMovies(type: String) : Flow<Resource<List<MovieModel>>>

	fun getMovieRecommendation(movieId: Int) : Flow<Resource<List<MovieModel>>>

	fun searchMovie(query: String) : Flow<Resource<List<MovieModel>>>

	fun getBookmarkedMovies() : Flow<List<MovieModel>>

	fun setBookmarkedMovie(movieModel: MovieModel, bookmarked: Boolean)
}

/*

class MyMovieDiffUtils(
	private val oldList: List<MovieModel>,
	private val newList: List<MovieModel>,
): DiffUtil.Callback() {
	override fun getOldListSize(): Int {
		return oldList.size
	}

	override fun getNewListSize(): Int {
		return newList.size
	}

	override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return oldList[oldItemPosition].id == newList[newItemPosition].id
	}

	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return oldList[oldItemPosition] == newList[newItemPosition]
	}

}
 */
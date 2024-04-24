package com.quiraadev.core.utils

import com.quiraadev.core.data.source.local.entity.MovieEntity
import com.quiraadev.core.data.source.remote.response.MovieResult
import com.quiraadev.core.domain.model.MovieModel

object DataMapper {

	fun mapMovieResponseToMovieEntity(response: List<MovieResult>, type: String): List<MovieEntity> {
		val movieList = ArrayList<MovieEntity>()
		response.map { movieResponse ->
			val movie = MovieEntity(
				id = movieResponse.id ?: 0,
				title = movieResponse.title ?: "",
				overview = movieResponse.overview ?: "",
				backdropPath = movieResponse.backdropPath ?: "",
				posterPath = movieResponse.posterPath ?: "",
				originalTitle = movieResponse.originalTitle ?: "",
				originalLanguage = movieResponse.originalLanguage ?: "",
				releaseDate = movieResponse.releaseDate ?: "",
				popularity = movieResponse.popularity ?: 0.0,
				voteAverage = movieResponse.voteAverage ?: 0.0,
				voteCount = movieResponse.voteCount ?: 0,
				movieType = type
			)
			movieList.add(movie)
		}
		return movieList
	}



	fun mapMovieEntityToMovieModel(entities: List<MovieEntity>): List<MovieModel> {
		val movieList = ArrayList<MovieModel>()
		entities.map { entity ->
			val movie = MovieModel(
				id = entity.id,
				title = entity.title,
				overview = entity.overview,
				backdropPath = entity.backdropPath,
				posterPath = entity.posterPath,
				originalTitle = entity.originalTitle,
				originalLanguage = entity.originalLanguage,
				releaseDate = entity.releaseDate,
				popularity = entity.popularity,
				voteAverage = entity.voteAverage,
				voteCount = entity.voteCount,
				isFavorite = entity.isFavorite,
				movieType = entity.movieType
			)
			movieList.add(movie)
		}
		return movieList
	}

	fun mapMovieResponseToModel(response: MovieResult) = MovieModel(
		overview = response.overview,
		originalLanguage = response.originalLanguage,
		originalTitle = response.originalTitle,
		title = response.title,
		posterPath = response.posterPath,
		backdropPath = response.backdropPath,
		releaseDate = response.releaseDate,
		popularity = response.popularity,
		voteAverage = response.voteAverage,
		id = response.id,
		voteCount = response.voteCount,
	)


	fun mapMovieModelToEntity(model: MovieModel): MovieEntity = MovieEntity(
		id = model.id ?: 0,
		title = model.title ?: "",
		overview = model.overview ?: "",
		backdropPath = model.backdropPath ?: "",
		posterPath = model.posterPath ?: "",
		originalTitle = model.originalTitle ?: "",
		originalLanguage = model.originalLanguage ?: "",
		releaseDate = model.releaseDate ?: "",
		popularity = model.popularity ?: 0.0,
		voteAverage = model.voteAverage ?: 0.0,
		voteCount = model.voteCount ?: 0,
		movieType = model.movieType ?: "",
		isFavorite = model.isFavorite
	)
}
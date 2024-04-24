package com.quiraadev.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieModel(
	val id: Int? = null,
	val title: String? = null,
	val overview: String? = null,
	val backdropPath: String? = null,
	val posterPath: String? = null,
	val originalTitle: String? = null,
	val originalLanguage: String? = null,
	val releaseDate: String? = null,
	val popularity: Double? = null,
	val voteAverage: Double? = null,
	val voteCount: Int? = null,
	val movieType: String? = null,
	var isFavorite: Boolean = false,
): Parcelable
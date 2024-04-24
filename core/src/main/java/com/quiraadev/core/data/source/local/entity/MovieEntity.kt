package com.quiraadev.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_movie")
data class MovieEntity(
	@PrimaryKey
	val id: Int,
	@ColumnInfo("title")
	val title: String,
	@ColumnInfo("overview")
	val overview: String,
	@ColumnInfo("backdropPath")
	val backdropPath: String,
	@ColumnInfo("posterPath")
	val posterPath: String,
	@ColumnInfo("originalTitle")
	val originalTitle: String,
	@ColumnInfo("originalLanguage")
	val originalLanguage: String,
	@ColumnInfo("releaseDate")
	val releaseDate: String,
	@ColumnInfo("popularity")
	val popularity: Double,
	@ColumnInfo("voteAverage")
	val voteAverage: Double,
	@ColumnInfo("voteCount")
	val voteCount: Int,
	@ColumnInfo("movieType")
	val movieType: String,
	@ColumnInfo("isFavorite")
	var isFavorite: Boolean = false
)

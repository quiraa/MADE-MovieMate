package com.quiraadev.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.quiraadev.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieMateDao {

	@Query("SELECT * FROM tbl_movie WHERE movieType = :type")
	fun getAllMovie(type:String) : Flow<List<MovieEntity>>



	@Query("SELECT * FROM tbl_movie WHERE isFavorite = 1")
	fun getBookmarkedMovies() : Flow<List<MovieEntity>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertAllMovie(movieEntity: List<MovieEntity>)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertMovie(movieEntity: MovieEntity)

	@Delete
	suspend fun deleteMovie(movieEntity: MovieEntity)

	@Update
	fun updateBookmarkedMovie(movieEntity: MovieEntity)

}
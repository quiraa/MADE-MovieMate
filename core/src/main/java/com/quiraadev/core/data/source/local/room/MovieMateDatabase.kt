package com.quiraadev.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.quiraadev.core.data.source.local.entity.MovieEntity

@Database(
	entities = [MovieEntity::class],
	version = 2,
	exportSchema = false,
)
abstract class MovieMateDatabase : RoomDatabase() {
	abstract val dao: MovieMateDao
}
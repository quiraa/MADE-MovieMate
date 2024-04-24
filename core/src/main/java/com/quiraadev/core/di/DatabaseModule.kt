package com.quiraadev.core.di

import android.content.Context
import androidx.room.Room
import com.quiraadev.core.data.source.local.room.MovieMateDao
import com.quiraadev.core.data.source.local.room.MovieMateDatabase
import com.quiraadev.core.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

	@Provides
	@Singleton
	fun provideLocalDatabase(
		@ApplicationContext context: Context
	): MovieMateDatabase {
		val passphrase = SQLiteDatabase.getBytes("movieMate".toCharArray())
		val factory = SupportFactory(passphrase)
		return Room.databaseBuilder(
			context = context,
			klass = MovieMateDatabase::class.java,
			name = Constants.DATABASE_NAME
		).fallbackToDestructiveMigration().openHelperFactory(factory).build()
	}

	@Provides
	fun providesDao(database: MovieMateDatabase): MovieMateDao = database.dao
}
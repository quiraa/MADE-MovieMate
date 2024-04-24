package com.quiraadev.core.di

import com.quiraadev.core.data.repository.MovieMateRepositoryImpl
import com.quiraadev.core.domain.repository.MovieMateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

	@Binds
	abstract fun provideRepository(movieMateRepository: MovieMateRepositoryImpl) : MovieMateRepository
}
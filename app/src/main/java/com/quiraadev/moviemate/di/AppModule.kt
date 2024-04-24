package com.quiraadev.moviemate.di

import com.quiraadev.core.domain.usecase.MovieMateInteractor
import com.quiraadev.core.domain.usecase.MovieMateUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

	@Binds
	abstract fun provideMovieMateUseCase(movieMateInteractor: MovieMateInteractor) : MovieMateUseCase
}
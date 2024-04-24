package com.quiraadev.moviemate.di

import com.quiraadev.core.domain.usecase.MovieMateUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {

	fun movieMateUseCase() : MovieMateUseCase
}
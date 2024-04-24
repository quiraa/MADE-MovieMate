package com.quiraadev.moviemate.favorite.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.quiraadev.core.domain.usecase.MovieMateUseCase
import com.quiraadev.moviemate.favorite.presentation.FavoriteViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(
	private val movieMateUseCase: MovieMateUseCase
) :ViewModelProvider.NewInstanceFactory() {

	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return when {
			modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
				FavoriteViewModel(movieMateUseCase) as T
			}

			 else -> throw Throwable("Unknown ViewModel class : ${modelClass.name}")
		}
	}
}
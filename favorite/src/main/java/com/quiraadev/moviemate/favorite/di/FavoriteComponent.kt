package com.quiraadev.moviemate.favorite.di

import android.content.Context
import com.quiraadev.moviemate.di.FavoriteModuleDependencies
import com.quiraadev.moviemate.favorite.presentation.FavoriteFragment
import dagger.BindsInstance
import dagger.Component

@Component(
	dependencies = [FavoriteModuleDependencies::class]
)
interface FavoriteComponent {

	fun inject(fragment: FavoriteFragment)

	@Component.Builder
	interface Builder {
		fun context(@BindsInstance context: Context) : Builder
		fun appDependencies(favoriteDependencies: FavoriteModuleDependencies) : Builder
		fun build() : FavoriteComponent
	}
}
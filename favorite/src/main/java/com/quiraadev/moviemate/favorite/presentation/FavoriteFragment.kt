package com.quiraadev.moviemate.favorite.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.quiraadev.core.ui.MovieListAdapter
import com.quiraadev.moviemate.di.FavoriteModuleDependencies
import com.quiraadev.moviemate.favorite.R
import com.quiraadev.moviemate.favorite.databinding.FragmentFavoriteBinding
import com.quiraadev.moviemate.favorite.di.DaggerFavoriteComponent
import com.quiraadev.moviemate.favorite.factory.ViewModelFactory
import com.quiraadev.moviemate.presentation.detail.DetailActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
	private val binding: FragmentFavoriteBinding by viewBinding()

	@Inject
	lateinit var factory: ViewModelFactory

	private val viewModel: FavoriteViewModel by viewModels {
		factory
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		DaggerFavoriteComponent.builder()
			.context(requireActivity().applicationContext)
			.appDependencies(
				EntryPointAccessors.fromApplication(
					requireActivity().applicationContext,
					FavoriteModuleDependencies::class.java
				)
			).build().inject(this)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val movieAdapter = MovieListAdapter()
		movieAdapter.onMovieClick = { selectedMovie ->
			val intent = Intent(activity, DetailActivity::class.java)
			intent.putExtra(DetailActivity.EXTRA_MOVIE, selectedMovie)
			startActivity(intent)
		}

		viewModel.favoriteList.observe(viewLifecycleOwner) { favorites ->
			if(favorites.isNullOrEmpty()) {
				binding.emptyContent.root.isVisible = true
				binding.emptyContent.viewEmptyText.text = getString(com.quiraadev.moviemate.R.string.text_empty_bookmark)
				binding.rvFavorites.isVisible = false
				return@observe
			}
			movieAdapter.setData(favorites)

			binding.rvFavorites.isVisible = true
			binding.emptyContent.root.isVisible = false
			binding.rvFavorites.layoutManager = LinearLayoutManager(activity)
			binding.rvFavorites.adapter = movieAdapter
		}
	}
}
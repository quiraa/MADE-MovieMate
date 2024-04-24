package com.quiraadev.moviemate.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.quiraadev.core.ui.MovieListAdapter
import com.quiraadev.moviemate.R
import com.quiraadev.moviemate.databinding.FragmentHomeBinding
import com.quiraadev.moviemate.presentation.detail.DetailActivity
import com.quiraadev.moviemate.presentation.home.event.HomeEvent
import com.quiraadev.moviemate.presentation.home.event.HomeState
import com.quiraadev.moviemate.presentation.home.event.MovieCategory
import com.quiraadev.moviemate.presentation.settings.SettingsActivity
import com.quiraadev.moviemate.utils.triggerSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
	private val binding: FragmentHomeBinding by viewBinding()
	private val homeViewModel: HomeViewModel by viewModels()


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val movieAdapter = MovieListAdapter()
		movieAdapter.onMovieClick = { selectedMovie ->
			val intent = Intent(activity, DetailActivity::class.java)
			intent.putExtra(DetailActivity.EXTRA_MOVIE, selectedMovie)
			startActivity(intent)
		}

		homeViewModel.uiState.observe(viewLifecycleOwner) { state ->
			var isLoading = true
			var isMessageShown = false
			if (state != null) {
				when (state) {
					is HomeState.Error -> {
						isLoading = false
						isMessageShown = true
						binding.viewError.textContentError.text = state.message
						triggerSnackbar(binding.root, state.message)
					}
					is HomeState.Loading -> {
						isLoading = true
						isMessageShown = false
					}
					is HomeState.Success -> {
						isLoading = false
						isMessageShown = false
						homeViewModel.movieList.observe(viewLifecycleOwner) { movieList ->
							movieAdapter.setData(movieList)
						}
					}
				}
				binding.progressCircular.isVisible = isLoading
				binding.rvMovies.isVisible = !isLoading
				binding.viewError.root.isVisible = isMessageShown
				binding.rvMovies.layoutManager = LinearLayoutManager(context)
				binding.rvMovies.adapter = movieAdapter
			}
		}

		setupAppBarMenu()
		setupCategoryChips()
	}

	private fun setupAppBarMenu() {
		binding.homeToolbar.setOnMenuItemClickListener { menuItem ->
			when(menuItem.itemId) {
				R.id.item_settings -> {
					val intent = Intent(activity, SettingsActivity::class.java)
					startActivity(intent)
					true
				}

				else -> false
			}
		}
	}

	private fun setupCategoryChips() {
		binding.chipCategoryGroup.setOnCheckedStateChangeListener { _, _ ->
			val checkedChip = when {
				binding.chipPopular.isChecked -> 1
				binding.chipUpcoming.isChecked -> 2
				binding.chipTopRated.isChecked -> 3
				else -> throw Exception("Unknown Filter Chip")
			}
			val category = when (checkedChip) {
				1 -> MovieCategory.POPULAR
				2 -> MovieCategory.UPCOMING
				3 -> MovieCategory.TOP_RATED
				else -> throw Exception("Unknown Filter Chip")
			}
			homeViewModel.onEvent(HomeEvent.ChangeCategoryEvent(category))
		}
	}
}

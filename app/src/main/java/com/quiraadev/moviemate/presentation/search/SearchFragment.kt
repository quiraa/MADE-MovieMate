package com.quiraadev.moviemate.presentation.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.quiraadev.core.ui.MovieListAdapter
import com.quiraadev.moviemate.R
import com.quiraadev.moviemate.databinding.FragmentSearchBinding
import com.quiraadev.moviemate.presentation.detail.DetailActivity
import com.quiraadev.moviemate.presentation.search.event.SearchState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

	private val binding: FragmentSearchBinding by viewBinding()
	private val searchViewModel: SearchViewModel by viewModels()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val movieAdapter = MovieListAdapter()
		movieAdapter.onMovieClick = { selectedMovie ->
			val intent = Intent(activity, DetailActivity::class.java)
			intent.putExtra(DetailActivity.EXTRA_MOVIE, selectedMovie)
			startActivity(intent)
		}

		searchViewModel.uiState.observe(viewLifecycleOwner) { state ->
			if (state != null) {
				var isLoading = false

				when (state) {
					is SearchState.EmptyQuery -> {
						isLoading = false
						binding.rvSearchMovies.isVisible = false
						binding.contentEmpty.root.isVisible = false
						binding.contentSearch.root.isVisible = true
					}

					is SearchState.Error -> {
						isLoading = false
						binding.contentEmpty.root.isVisible = true
						binding.contentSearch.root.isVisible = false
						binding.contentEmpty.contentEmptyText.text = state.message
						binding.rvSearchMovies.isVisible = false
					}

					is SearchState.Loading -> {
						isLoading = true
						binding.contentEmpty.root.isVisible = false
						binding.contentSearch.root.isVisible = false
						binding.rvSearchMovies.isVisible = false
					}

					is SearchState.Success -> {
						isLoading = false
						binding.contentEmpty.root.isVisible = false
						binding.contentSearch.root.isVisible = false
						searchViewModel.searchedMovies.observe(viewLifecycleOwner) { movieList ->
							if(movieList.isNullOrEmpty()) {
								binding.contentEmpty.root.isVisible = true
								binding.rvSearchMovies.isVisible = false
								binding.contentSearch.root.isVisible = false
							} else {
								binding.contentEmpty.root.isVisible = false
								binding.contentSearch.root.isVisible = false
								movieAdapter.setData(movieList)
							}
						}
					}
				}
				binding.searchProgressCircular.isVisible = isLoading
				binding.rvSearchMovies.isVisible = !isLoading
				binding.rvSearchMovies.layoutManager = LinearLayoutManager(context)
				binding.rvSearchMovies.adapter = movieAdapter
			}
		}

		binding.searchInputLayout.editText?.addTextChangedListener { text ->
			searchViewModel.onQueryTextChanged(text.toString())
		}
	}
}
package com.quiraadev.moviemate.presentation.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.quiraadev.core.domain.model.MovieModel
import com.quiraadev.core.ui.MovieListAdapter
import com.quiraadev.core.utils.Constants
import com.quiraadev.moviemate.R
import com.quiraadev.moviemate.databinding.ActivityDetailBinding
import com.quiraadev.moviemate.presentation.detail.event.DetailEvent
import com.quiraadev.moviemate.presentation.detail.event.DetailState
import com.quiraadev.moviemate.utils.triggerSnackbar
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class DetailActivity : AppCompatActivity(R.layout.activity_detail) {
	private val binding: ActivityDetailBinding by viewBinding()
	private val detailViewModel: DetailViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setSupportActionBar(binding.detailToolbar)
		binding.detailToolbar.setNavigationOnClickListener { finish() }

		val movieAdapter = MovieListAdapter()
		movieAdapter.onMovieClick = { selectedMovie ->
			val intent = Intent(this, DetailActivity::class.java)
			intent.putExtra(EXTRA_MOVIE, selectedMovie)
			startActivity(intent)
		}

		val detailMovie = intent.getParcelableExtra<MovieModel>(EXTRA_MOVIE)
		detailViewModel.onEvent(DetailEvent.GetMovieRecommendationEvent(detailMovie?.id ?: 0))

		detailViewModel.uiState.observe(this) { state ->
			var isLoading = true
			var isMessageShown = false
			if(state != null) {
				when(state) {
					is DetailState.Error -> {
						isLoading = false
						isMessageShown = true
						binding.viewError.textContentError.text = state.message
						triggerSnackbar(binding.root, state.message)
					}
					is DetailState.Loading -> {
						isLoading = true
						isMessageShown = false
					}
					is DetailState.Success -> {
						isLoading = false
						isMessageShown = false
						setDetailMovie(detailMovie)
						detailViewModel.recommendedMovies.observe(this) { recommendedMovies ->
							movieAdapter.setData(recommendedMovies)
						}
					}
				}
				binding.progressIndicator.isVisible = isLoading
				binding.rvMovieRecommendation.isVisible = !isLoading
				binding.viewError.root.isVisible = isMessageShown
				binding.rvMovieRecommendation.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
				binding.rvMovieRecommendation.adapter = movieAdapter
			}
		}
	}

	private fun setDetailMovie(detailMovie: MovieModel?) {
		detailMovie?.let { movie ->
			binding.tvMovieTitle.text = movie.originalTitle
			binding.tvMovieOverview.text = movie.overview
			binding.tvMoviePopularity.text = movie.popularity.toString()
			binding.tvMovieReleaseDate.text = movie.releaseDate
			binding.ivMovieToolbar.load(Constants.API_BASE_URL + movie.backdropPath)
			binding.detailCollapsingToolbar.title = movie.title

			setBookmarkStatus(movie.isFavorite)
			binding.bookmarkBtn.setOnClickListener {
				movie.isFavorite = !movie.isFavorite
				detailViewModel.onEvent(DetailEvent.SetMovieBookmarkEvent(movie, movie.isFavorite))
				setBookmarkStatus(movie.isFavorite)
			}
		}
	}

	private fun setBookmarkStatus(newStatus : Boolean) {
		if(newStatus) {
			binding.bookmarkBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_bookmark_filled))
		} else {
			binding.bookmarkBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_bookmark_outlined))
		}
	}

	companion object {
		const val EXTRA_MOVIE = "extra_movie_data"
	}
}
package com.quiraadev.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.quiraadev.core.databinding.ItemMovieBinding
import com.quiraadev.core.domain.model.MovieModel
import com.quiraadev.core.utils.Constants

class MovieListAdapter :
	RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

	private var listMovies = emptyList<MovieModel>()
	var onMovieClick: ((movie: MovieModel) -> Unit)? = null

	fun setData(newList: List<MovieModel>) {
		val diffUtil = MyMovieDiffUtils(listMovies, newList)
		val diffResults = DiffUtil.calculateDiff(diffUtil)
		listMovies = newList
		diffResults.dispatchUpdatesTo(this)
	}

	inner class MovieListViewHolder(private val binding: ItemMovieBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(data: MovieModel) {
			binding.apply {
				ivMovie.load(Constants.BASE_IMAGE_URL + data.backdropPath)
				tvTitleMovie.text = data.title
			}
		}

		init {
			binding.root.setOnClickListener {
				onMovieClick?.invoke(listMovies[adapterPosition])
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
		val view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return MovieListViewHolder(view)
	}

	override fun getItemCount(): Int = listMovies.size

	override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
		holder.bind(listMovies[position])
	}
}

class MyMovieDiffUtils(
	private val oldList: List<MovieModel>,
	private val newList: List<MovieModel>,
): DiffUtil.Callback() {
	override fun getOldListSize(): Int {
		return oldList.size
	}

	override fun getNewListSize(): Int {
		return newList.size
	}

	override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return oldList[oldItemPosition].id == newList[newItemPosition].id
	}

	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return oldList[oldItemPosition] == newList[newItemPosition]
	}

}
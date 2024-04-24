package com.quiraadev.core.data.source.remote

import android.util.Log
import com.quiraadev.core.data.source.remote.network.ApiService
import com.quiraadev.core.data.source.remote.network.ResponseStatus
import com.quiraadev.core.data.source.remote.response.MovieResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
	private val apiService: ApiService
) {

	fun getMovieList(type: String): Flow<ResponseStatus<List<MovieResult>>> = flow {
		try {
			val result = apiService.getAllMovies(type)?.results
			if (!result.isNullOrEmpty()) emit(ResponseStatus.Success(result))
			else emit(ResponseStatus.Empty)
		} catch (error: Exception) {
			emit(ResponseStatus.Error(error.message.toString()))
			Log.e(TAG, error.localizedMessage ?: "")
		}
	}.flowOn(Dispatchers.IO)

	fun getMovieRecommendations(
		movieId: Int
	): Flow<ResponseStatus<List<MovieResult>>> = flow {
		try {
			val result = apiService.getMovieRecommendations(movieId)?.results
			if (!result.isNullOrEmpty()) emit(ResponseStatus.Success(result))
			else emit(ResponseStatus.Empty)
		} catch (error: Exception) {
			emit(ResponseStatus.Error(error.message.toString()))
			Log.e(TAG, error.localizedMessage ?: "")
		}
	}.flowOn(Dispatchers.IO)

	fun searchMovie(
		query: String
	): Flow<ResponseStatus<List<MovieResult>>> = flow {
		try {
			val result = apiService.searchMovies(query)?.results
			if (!result.isNullOrEmpty()) emit(ResponseStatus.Success(result))
			else emit(ResponseStatus.Empty)
		} catch (error: Exception) {
			emit(ResponseStatus.Error(error.message.toString()))
			Log.e(TAG, error.localizedMessage ?: "")
		}
	}.flowOn(Dispatchers.IO)

	companion object {
		private const val TAG = "RemoteDataSource"
	}
}
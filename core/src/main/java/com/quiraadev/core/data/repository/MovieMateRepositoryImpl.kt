package com.quiraadev.core.data.repository

import android.util.Log
import com.quiraadev.core.data.NetworkBoundResource
import com.quiraadev.core.data.Resource
import com.quiraadev.core.data.source.local.LocalDataSource
import com.quiraadev.core.data.source.remote.RemoteDataSource
import com.quiraadev.core.data.source.remote.network.ResponseStatus
import com.quiraadev.core.data.source.remote.response.MovieResult
import com.quiraadev.core.domain.model.MovieModel
import com.quiraadev.core.domain.repository.MovieMateRepository
import com.quiraadev.core.utils.AppExecutors
import com.quiraadev.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieMateRepositoryImpl @Inject constructor(
	private val remoteDataSource: RemoteDataSource,
	private val localDataSource: LocalDataSource,
	private val appExecutors: AppExecutors,
) : MovieMateRepository {

	override fun getAllMovies(type: String): Flow<Resource<List<MovieModel>>> =
		object : NetworkBoundResource<List<MovieModel>, List<MovieResult>>() {
			override fun loadFromDB(): Flow<List<MovieModel>> =
				localDataSource.getMovieList(type).map { movieEntities ->
					DataMapper.mapMovieEntityToMovieModel(movieEntities)
				}

			override suspend fun createCall(): Flow<ResponseStatus<List<MovieResult>>> {
				return remoteDataSource.getMovieList(type)
			}

			override suspend fun saveCallResult(data: List<MovieResult>) {
				val movieEntities = DataMapper.mapMovieResponseToMovieEntity(data, type)
				localDataSource.insertAllMovie(movieEntities)
			}

			override fun shouldFetch(data: List<MovieModel>?): Boolean {
				return data.isNullOrEmpty()
			}
		}.asFlow()

	override fun getMovieRecommendation(movieId: Int): Flow<Resource<List<MovieModel>>> = flow {
		emit(Resource.Loading())
		when (val response =
			remoteDataSource.getMovieRecommendations(movieId).first()) {
			is ResponseStatus.Success -> {
				val movieModelList = response.data.map { movieRecommendations ->
					Log.d("MovieMateRepositoryImpl", "$movieRecommendations")
					DataMapper.mapMovieResponseToModel(movieRecommendations)
				}
				emit(Resource.Success(movieModelList))
			}

			is ResponseStatus.Error -> emit(Resource.Error(response.errorMessage))
			is ResponseStatus.Empty -> emit(Resource.Success())
		}
	}

	override fun searchMovie(query: String): Flow<Resource<List<MovieModel>>> = flow {
		emit(Resource.Loading())
		when (val response = remoteDataSource.searchMovie(query).first()) {
			is ResponseStatus.Success -> {
				val movieModels = response.data.map { searchedMovies ->
					DataMapper.mapMovieResponseToModel(searchedMovies)
				}
				emit(Resource.Success(movieModels))
			}

			is ResponseStatus.Error -> emit(Resource.Error(response.errorMessage))
			is ResponseStatus.Empty -> emit(Resource.Success())
		}
	}

	override fun getBookmarkedMovies(): Flow<List<MovieModel>> {
		return localDataSource.getBookmarkedMovieList().map { movieEntity ->
			DataMapper.mapMovieEntityToMovieModel(movieEntity)
		}
	}

	override fun setBookmarkedMovie(movieModel: MovieModel, bookmarked: Boolean) {
		val movieEntity = DataMapper.mapMovieModelToEntity(movieModel)
		appExecutors.diskIO().execute {
			localDataSource.updateBookmarkedMovie(movieEntity, bookmarked)
		}
	}
}
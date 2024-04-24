package com.quiraadev.core.data.source.remote.network

import com.quiraadev.core.data.source.remote.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

	@Headers("accept:application/json")
	@GET("3/movie/{type}")
	suspend fun getAllMovies(@Path("type") type: String): MovieResponse?

	@Headers("accept:application/json")
	@GET("3/movie/{movie_id}/recommendations")
	suspend fun getMovieRecommendations(@Path("movie_id") movieId: Int): MovieResponse?

	@Headers("accept:application/json")
	@GET("3/search/movie")
	suspend fun searchMovies(@Query("query") query: String): MovieResponse?
}
package com.quiraadev.core.di

import androidx.multidex.BuildConfig
import com.quiraadev.core.data.source.remote.network.ApiService
import com.quiraadev.core.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

	@Provides
	@Singleton
	fun provideOkHttpClient(): OkHttpClient {
		val hostName = "api.themoviedb.org"
		val certificatePinner = CertificatePinner.Builder()
			.add(hostName, "sha256/5VLcahb6x4EvvFrCF2TePZulWqrLHS2jCg9Ywv6JHog=")
			.add(hostName, "sha256/vxRon/El5KuI4vx5ey1DgmsYmRY0nDd5Cg4GfJ8S+bg=")
			.build()

		val client = OkHttpClient.Builder()
			.addInterceptor(
				HttpLoggingInterceptor().setLevel(
					if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
					else HttpLoggingInterceptor.Level.NONE
				)
			)
			.readTimeout(60, TimeUnit.SECONDS)
			.connectTimeout(60, TimeUnit.SECONDS)
			.certificatePinner(certificatePinner)
			.addInterceptor { chain ->
				val request = chain.request().newBuilder().addHeader(
					name = "Authorization",
					value = "Bearer ${Constants.ACCESS_TOKEN}"
				).build()
				chain.proceed(request)
			}

		return client.build()
	}

	@Provides
	@Singleton
	fun provideApiService(client: OkHttpClient): ApiService {
		val retrofit = Retrofit.Builder()
			.baseUrl(Constants.API_BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.client(client)
			.build()

		return retrofit.create(ApiService::class.java)
	}
}
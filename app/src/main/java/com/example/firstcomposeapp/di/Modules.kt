package com.example.firstcomposeapp.di

import com.example.firstcomposeapp.rest.MovieRepository
import com.example.firstcomposeapp.rest.MovieRepositoryImpl
import com.example.firstcomposeapp.rest.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import java.util.logging.Level

@Module
@InstallIn(SingletonComponent::class)
class Modules {

    @Provides
    fun providesLoggingInterceptor() : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor : HttpLoggingInterceptor) : OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    @Provides
    fun providesMovieService(okHttpClient: OkHttpClient) : MovieService =
        Retrofit.Builder()
            .baseUrl(MovieService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MovieService::class.java)

    @Provides
    fun providesMovieRepository(movieService: MovieService) : MovieRepository =
        MovieRepositoryImpl(movieService)

    @Provides
    fun providesDispatcher() : CoroutineDispatcher = Dispatchers.IO
}
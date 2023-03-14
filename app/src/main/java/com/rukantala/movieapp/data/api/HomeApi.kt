package com.rukantala.movieapp.data.api

import com.rukantala.movieapp.data.model.GenreItem
import com.rukantala.movieapp.data.model.MovieItem
import com.rukantala.movieapp.utils.ResponseListWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeApi {
    @GET("list/{list_id}")
    suspend fun getAllMovies(
        @Path("list_id") page: Int = 1
    ): Response<ResponseListWrapper<MovieItem>>

    @GET("genre/movie/list")
    suspend fun fetchAllGenre(): Response<ResponseListWrapper<GenreItem>>

    @GET("discover/movie")
    suspend fun fetchMovieByGenre(
        @Query("with_genres") genre: String,
        @Query("page") page: String
    ): Response<ResponseListWrapper<MovieItem>>
}
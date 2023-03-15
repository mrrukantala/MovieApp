package com.rukantala.movieapp.data.api

import com.rukantala.movieapp.data.model.*
import com.rukantala.movieapp.utils.ResponseListGenreWrapper
import com.rukantala.movieapp.utils.ResponseListWrapper
import com.rukantala.movieapp.utils.ResponseObjectWrapper
import com.rukantala.movieapp.utils.ResponseReviewWrapper
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
    suspend fun fetchAllGenre(): Response<ResponseListGenreWrapper<GenreItem>>

    @GET("discover/movie")
    suspend fun fetchMovieByGenre(
        @Query("with_genres") genre: String,
        @Query("page") page: String
    ): Response<ResponseReviewWrapper<MovieItem>>

    @GET("movie/{movie_id}")
    suspend fun fetchDetailMovie(
        @Path("movie_id") movieId: String
    ): Response<DetailMovieItem>

    @GET("movie/{movie_id}/videos")
    suspend fun fetchVideoMovie(
        @Path("movie_id") movieId: String
    ): Response<ResponseReviewWrapper<VideoItem>>

    @GET("movie/{movie_id}/reviews")
    suspend fun fetchReview(
        @Path("movie_id") movieId: String,
        @Query("page") page: String
    ): Response<ResponseReviewWrapper<ReviewItem>>
}
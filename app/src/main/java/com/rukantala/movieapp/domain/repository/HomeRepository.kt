package com.rukantala.movieapp.domain.repository

import androidx.annotation.WorkerThread
import com.rukantala.movieapp.domain.entity.*
import com.rukantala.movieapp.utils.BasicResponse
import com.rukantala.movieapp.utils.Result
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllMoviews(page: Int):
            Flow<Result<List<MovieEntity>, BasicResponse>>

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun fetchAllGenre():
            Flow<Result<List<GenreEntity>, BasicResponse>>

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun fetchMovieByGenre(genre: String, page: String):
            Flow<Result<List<MovieEntity>, BasicResponse>>


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun fetchDetailMovie(movieId: String): Flow<Result<DetailMovieEntity, BasicResponse>>

    suspend fun fetchVideoMovie(movieId: String): Flow<Result<List<VideoEntity>, BasicResponse>>


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun fetchReview(
        movieId: String,
        page: String
    ): Flow<Result<List<ReviewEntity>, BasicResponse>>
}
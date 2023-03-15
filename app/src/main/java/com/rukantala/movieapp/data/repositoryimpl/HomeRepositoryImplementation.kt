package com.rukantala.movieapp.data.repositoryimpl

import android.util.Log
import com.rukantala.movieapp.data.api.HomeApi
import com.rukantala.movieapp.domain.entity.*
import com.rukantala.movieapp.domain.repository.HomeRepository
import com.rukantala.movieapp.utils.BasicResponse
import com.rukantala.movieapp.utils.Result
import com.rukantala.movieapp.utils.isNotSuccesfull
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImplementation @Inject constructor(
    private val api: HomeApi
) : HomeRepository {
    override suspend fun getAllMoviews(page: Int): Flow<Result<List<MovieEntity>, BasicResponse>> {
        return flow {
            delay(300)
            val response = api.getAllMovies(page)

            if (response.isSuccessful) {
                val body = response.body()?.data
                val data = mutableListOf<MovieEntity>()
                body?.forEach { data.add(it.toMovieEntity()) }

                emit(Result.Success(data))
            } else {
                emit(Result.Error(isNotSuccesfull(response.errorBody()!!)))
            }
        }
    }

    override suspend fun fetchAllGenre(): Flow<Result<List<GenreEntity>, BasicResponse>> {
        return flow {
            delay(300)
            val response = api.fetchAllGenre()

            if (response.isSuccessful) {
                val body = response.body()?.data
                val data = mutableListOf<GenreEntity>()
                body?.forEach { data.add(it.toGenreEntity()) }
                emit(Result.Success(data))
            } else {
                emit(Result.Error(isNotSuccesfull(response.errorBody()!!)))
            }
        }
    }

    override suspend fun fetchMovieByGenre(
        genre: String,
        page: String
    ): Flow<Result<List<MovieEntity>, BasicResponse>> {
        return flow {
            delay(300)
            val response = api.fetchMovieByGenre(genre, page)

            if (response.isSuccessful) {
                val body = response.body()?.data
                Log.v("DD", body.toString())
                val data = mutableListOf<MovieEntity>()
                body?.forEach { data.add(it.toMovieEntity()) }
                emit(Result.Success(data))
            } else {
                emit(Result.Error(isNotSuccesfull(response.errorBody()!!)))
            }
        }
    }

    override suspend fun fetchDetailMovie(movieId: String): Flow<Result<DetailMovieEntity, BasicResponse>> {
        return flow {
            delay(300)
            val response = api.fetchDetailMovie(movieId)

            if (response.isSuccessful) {
                emit(Result.Success(response.body()?.toDetailMovie()!!))
            } else {
                emit(Result.Error(isNotSuccesfull(response.errorBody()!!)))
            }
        }
    }

    override suspend fun fetchVideoMovie(movieId: String): Flow<Result<List<VideoEntity>, BasicResponse>> {
        return flow {
            delay(300)
            val response = api.fetchVideoMovie(movieId)
            if (response.isSuccessful) {
                val body = response.body()?.data
                val data = mutableListOf<VideoEntity>()
                body?.forEach { data.add(it.toVideoEntity()) }
                emit(Result.Success(data))
            } else {
                emit(Result.Error(isNotSuccesfull(response.errorBody()!!)))
            }
        }
    }

    override suspend fun fetchReview(
        movieId: String,
        page: String
    ): Flow<Result<List<ReviewEntity>, BasicResponse>> {
        return flow {
            delay(300)
            val response = api.fetchReview(movieId, page)
            if (response.isSuccessful) {
                val body = response.body()?.data
                val data = mutableListOf<ReviewEntity>()
                body?.forEach {
                    data.add(it.toReviewEntity())
                }
            } else {
                emit(Result.Error(isNotSuccesfull(response.errorBody()!!)))
            }
        }
    }
}
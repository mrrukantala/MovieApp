package com.rukantala.movieapp.data.repositoryimpl

import com.rukantala.movieapp.data.api.HomeApi
import com.rukantala.movieapp.data.model.MovieItem
import com.rukantala.movieapp.domain.entity.MovieEntity
import com.rukantala.movieapp.domain.repository.HomeRepository
import com.rukantala.movieapp.utils.BasicResponse
import com.rukantala.movieapp.utils.ResponseListWrapper
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
}
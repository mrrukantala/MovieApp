package com.rukantala.movieapp.domain.repository

import com.rukantala.movieapp.data.model.MovieItem
import com.rukantala.movieapp.domain.entity.MovieEntity
import com.rukantala.movieapp.utils.BasicResponse
import com.rukantala.movieapp.utils.ResponseListWrapper
import com.rukantala.movieapp.utils.Result
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getAllMoviews(page: Int):
            Flow<Result<List<MovieEntity>, BasicResponse>>
}
package com.rukantala.movieapp.domain.usecase

import com.rukantala.movieapp.domain.entity.MovieEntity
import com.rukantala.movieapp.domain.repository.HomeRepository
import com.rukantala.movieapp.utils.BasicResponse
import com.rukantala.movieapp.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val repo: HomeRepository) {
    suspend fun fetchAllMoviews(page: Int) = repo.getAllMoviews(page)
}
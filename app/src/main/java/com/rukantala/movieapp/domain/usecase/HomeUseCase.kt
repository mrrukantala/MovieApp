package com.rukantala.movieapp.domain.usecase

import com.rukantala.movieapp.domain.repository.HomeRepository
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val repo: HomeRepository) {
    suspend fun fetchAllMoviews(page: Int) = repo.getAllMoviews(page)
}
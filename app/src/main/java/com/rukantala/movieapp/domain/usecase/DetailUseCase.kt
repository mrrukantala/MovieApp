package com.rukantala.movieapp.domain.usecase

import com.rukantala.movieapp.domain.repository.HomeRepository
import javax.inject.Inject

class DetailUseCase @Inject constructor(private val repo: HomeRepository) {
    suspend fun fetchDetailMovie(movieId: String) = repo.fetchDetailMovie(movieId)

    suspend fun fetchVideoMovie(movieId: String) = repo.fetchVideoMovie(movieId)

    suspend fun fetchReview(movieId: String, page: String) = repo.fetchReview(movieId, page)
}
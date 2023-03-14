package com.rukantala.movieapp.domain.usecase

import com.rukantala.movieapp.domain.repository.HomeRepository
import javax.inject.Inject

class GenreUseCase @Inject constructor(private val repo: HomeRepository) {
    suspend fun fetchMovieByGenre(genre: String, page: String) = repo.fetchMovieByGenre(genre, page)

    suspend fun fetchAllGenre() = repo.fetchAllGenre()
}
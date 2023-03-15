package com.rukantala.movieapp.data.model

import com.google.gson.annotations.SerializedName
import com.rukantala.movieapp.domain.entity.DetailMovieEntity

data class DetailMovieItem(
    @field:SerializedName("id") val id: Int? = 0,
    @field:SerializedName("original_title") val originalTitle: String? = "",
    @field:SerializedName("overview") val overview: String? = "",
    @field:SerializedName("vote_average") val voteAverage: String? = "",
    @field:SerializedName("poster_path") val posterPath: String? = "",
    @field:SerializedName("backdrop_path") val imagePath: String? = "",
    @field:SerializedName("release_date") val releaseDate: String? = "",
    @field:SerializedName("title") val title: String? = "",
    @field:SerializedName("genres") val genres: List<GenreItem>? = mutableListOf()
) {
    fun toDetailMovie() = DetailMovieEntity(
        id.toString() ?: "",
        originalTitle ?: "",
        overview ?: "",
        voteAverage ?: "",
        posterPath ?: "",
        imagePath ?: "",
        releaseDate ?: "",
        title ?: "",
        genres?.map { it.toGenreEntity() } ?: listOf()
    )
}




package com.rukantala.movieapp.data.model

import com.google.gson.annotations.SerializedName
import com.rukantala.movieapp.domain.entity.DetailMovieEntity

data class DetailMovieItem(
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("original_title") val originalTitle: String? = "",
    @SerializedName("overview") val overview: String? = "",
    @SerializedName("vote_average") val voteAverage: String? = "",
    @SerializedName("poster_path") val posterPath: String? = "",
    @SerializedName("backdrop_path") val imagePath: String? = "",
    @SerializedName("release_date") val releaseDate: String? = "",
    @SerializedName("title") val title: String? = "",
    @SerializedName("genres") val genres: List<GenreItem>? = mutableListOf()
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




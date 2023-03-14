package com.rukantala.movieapp.data.model

import com.google.gson.annotations.SerializedName
import com.rukantala.movieapp.domain.entity.GenreEntity

data class GenreItem(
    @field:SerializedName("id") val id: Int? = 0,
    @field:SerializedName("name") val name: String? = ""
) {
    fun toGenreEntity() = GenreEntity(id ?: 0, name ?: "")
}

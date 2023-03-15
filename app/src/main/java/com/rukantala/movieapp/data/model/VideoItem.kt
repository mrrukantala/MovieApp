package com.rukantala.movieapp.data.model

import com.google.gson.annotations.SerializedName
import com.rukantala.movieapp.domain.entity.VideoEntity

data class VideoItem(
    @field:SerializedName("iso_639_1") val iso6391: String? = "",
    @field:SerializedName("iso_3166_1") val iso31661: String? = "",
    @field:SerializedName("name") val name: String? = "",
    @field:SerializedName("key") val key: String? = "",
    @field:SerializedName("id") val id: String? = ""
) {
    fun toVideoEntity() = VideoEntity(
        iso6391 ?: "",
        iso31661 ?: "",
        name ?: "",
        key ?: "",
        id ?: ""
    )
}

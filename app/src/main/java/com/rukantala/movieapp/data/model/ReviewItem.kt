package com.rukantala.movieapp.data.model

import com.google.gson.annotations.SerializedName
import com.rukantala.movieapp.domain.entity.AuthorDetailEntity
import com.rukantala.movieapp.domain.entity.ReviewEntity

data class ReviewItem(
    @field:SerializedName("id") val id: String? = "",
    @field:SerializedName("author") val author: String? = "",
    @field:SerializedName("author_detail") val authorDetail: AuthorDetailItem? = null,
    @field:SerializedName("content") val content: String? = "",
) {
    fun toReviewEntity() = ReviewEntity(
        id ?: "",
        author ?: "",
        authorDetail?.toAuthorDetailEntity() ?: AuthorDetailEntity(0.0),
        content ?: ""
    )
}

data class AuthorDetailItem(
    @field:SerializedName("rating") val rating: Double? = 0.0
) {
    fun toAuthorDetailEntity() = AuthorDetailEntity(rating ?: 0.0)
}

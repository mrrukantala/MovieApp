package com.rukantala.movieapp.domain.entity

data class ReviewEntity(
    val id: String,
    val author: String,
    val authorDetail: AuthorDetailEntity,
    val content: String
)


data class AuthorDetailEntity(
    val avatar: String,
    val rating: Double
)

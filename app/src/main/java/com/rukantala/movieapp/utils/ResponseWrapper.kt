package com.rukantala.movieapp.utils

import com.google.gson.annotations.SerializedName

data class ResponseListWrapper<T>(
    @SerializedName("items") val data: List<T>? = null
)

data class ResponseListGenreWrapper<T>(
    @SerializedName("genres") val data: List<T>? = null
)

data class ResponseReviewWrapper<T>(
    @SerializedName("results") val data: List<T>? = null
)

data class ResponseObjectWrapper<T>(
    @SerializedName("items") val data: T? = null
)


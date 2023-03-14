package com.rukantala.movieapp.utils

import com.google.gson.annotations.SerializedName

data class ResponseListWrapper<T>(
    @SerializedName("results") val data: List<T>? = null
)

data class ResponseObjectWrapper<T>(
    @SerializedName("result") val data: T? = null
)


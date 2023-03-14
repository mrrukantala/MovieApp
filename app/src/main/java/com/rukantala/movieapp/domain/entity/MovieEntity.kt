package com.rukantala.movieapp.domain.entity

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class MovieEntity(
    val id: String,
    val originalTitle: String,
    val overview: String,
    val popularity: String,
    val posterPath: String,
    val imagePath: String,
    val releaseDate: String,
    val title: String
) : Parcelable

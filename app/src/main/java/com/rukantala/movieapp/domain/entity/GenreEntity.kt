package com.rukantala.movieapp.domain.entity

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class GenreEntity(
    val id: Int,
    val name: String
) : Parcelable

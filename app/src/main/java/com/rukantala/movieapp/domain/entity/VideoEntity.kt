package com.rukantala.movieapp.domain.entity

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class VideoEntity(
    val iso6381: String,
    val iso31661: String,
    val name: String,
    val key: String,
    val id: String
) : Parcelable

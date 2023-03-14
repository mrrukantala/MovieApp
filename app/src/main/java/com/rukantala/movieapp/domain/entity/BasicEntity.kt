package com.rukantala.movieapp.domain.entity

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class BasicEntity(
    val status: Boolean?,
    val message: String?,
    val isSuccess: Boolean?
) : Parcelable
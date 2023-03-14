package com.rukantala.movieapp.presentation.home

import com.rukantala.movieapp.domain.entity.BasicEntity
import com.rukantala.movieapp.domain.entity.MovieEntity

interface HomeDataContract {

    fun fetchAllMovie()

    fun movieOnLoading()

    fun movieOnSuccess(data: List<MovieEntity>)

    fun movieOnEmpty(data: List<MovieEntity>)

    fun movieOnError(e: BasicEntity?)

    fun fetchLoadMoreMovie()

    fun movieLoadMoreOnLoading()

    fun movieLoadMoreOnSuccess(data: List<MovieEntity>)

    fun movieLoadMoreOnEmpty()

    fun movieLoadMoreOnError(e: BasicEntity?)
}
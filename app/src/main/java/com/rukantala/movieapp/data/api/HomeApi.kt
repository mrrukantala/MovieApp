package com.rukantala.movieapp.data.api

import com.rukantala.movieapp.data.model.MovieItem
import com.rukantala.movieapp.utils.ResponseListWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {

    @GET("list/1")
    suspend fun getAllMovies(
        @Query("page") page: Int
    ): Response<ResponseListWrapper<MovieItem>>
//{{BASE}}list/1/?page=1&api_key={{api_key}}
}
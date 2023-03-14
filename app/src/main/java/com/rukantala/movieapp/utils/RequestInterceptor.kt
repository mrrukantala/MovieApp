package com.rukantala.movieapp.utils

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        // add TheMovieDB api automatically every requests.
        val url = originalUrl.newBuilder()
            .addQueryParameter("api_key", "cbfb25534a0ff429cb9bf4e8b13bad97")
            .build()

        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
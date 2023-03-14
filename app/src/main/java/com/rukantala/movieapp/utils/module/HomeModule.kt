package com.rukantala.movieapp.utils.module

import com.rukantala.movieapp.data.api.HomeApi
import com.rukantala.movieapp.data.repositoryimpl.HomeRepositoryImplementation
import com.rukantala.movieapp.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class HomeModule {
    @Singleton
    @Provides
    fun provideHomeApi(retrofit: Retrofit): HomeApi {
        return retrofit.create(HomeApi::class.java)
    }

    @Singleton
    @Provides
    fun provideHomeRepository(homeApiClient: HomeApi): HomeRepository {
        return HomeRepositoryImplementation(homeApiClient)
    }
}
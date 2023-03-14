package com.rukantala.movieapp.presentation.genre

import androidx.lifecycle.ViewModel
import com.rukantala.movieapp.domain.usecase.GenreUseCase
import com.rukantala.movieapp.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(private val useCase: GenreUseCase) : ViewModel() {



}
package com.rukantala.movieapp.presentation

import androidx.lifecycle.ViewModel
import com.rukantala.movieapp.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: HomeUseCase
) : ViewModel() {
    // TODO: Implement the ViewModel
}
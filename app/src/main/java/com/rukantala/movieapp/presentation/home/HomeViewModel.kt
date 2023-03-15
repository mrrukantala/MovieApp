package com.rukantala.movieapp.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rukantala.movieapp.domain.entity.BasicEntity
import com.rukantala.movieapp.domain.entity.MovieEntity
import com.rukantala.movieapp.domain.usecase.HomeUseCase
import com.rukantala.movieapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: HomeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Init)
    val state get() = _state

    private val _lists = MutableStateFlow<List<MovieEntity>?>(mutableListOf())
    val list get() = _lists

    private val _stateLoadMore = MutableStateFlow<HomeLoadMoreState>(HomeLoadMoreState.Init)
    val stateLoadMore get() = _stateLoadMore

    private val _loadMoreList = MutableStateFlow<List<MovieEntity>?>(mutableListOf())
    val loadMoreList get() = _loadMoreList

    var page = 1

    fun fetchAllMovie() {
        page =1
        viewModelScope.launch {
            useCase.fetchAllMoviews(page)
                .onStart { _state.value = HomeState.Loading(true) }
                .catch { }
                .collect {
                    when (it) {
                        is Result.Success -> {
                            _lists.value = it.data
                            _state.value = HomeState.Success(_lists.value ?: listOf())
                        }
                        is Result.Error -> _state.value = HomeState.Error(it.response.toBasicEntity())
                    }
                }
        }
    }

    fun fetchLoadMoreMovie() {
        viewModelScope.launch {
            useCase.fetchAllMoviews(page)
                .onStart { _stateLoadMore.value = HomeLoadMoreState.Loading(true) }
                .catch { }
                .collect {
                    when (it) {
                        is Result.Success -> {
                            _lists.value = it.data
                            _stateLoadMore.value = HomeLoadMoreState.Success(_lists.value ?: listOf())
                        }
                        is Result.Error -> _stateLoadMore.value = HomeLoadMoreState.Error(it.response.toBasicEntity())
                    }
                }
        }
    }

}

sealed class HomeState {
    object Init : HomeState()
    data class Loading(val isLoading: Boolean = true) : HomeState()
    data class Success(val data: List<MovieEntity>) : HomeState()
    data class Empty(val data: List<MovieEntity> = mutableListOf()) : HomeState()
    data class Error(val data: BasicEntity?) : HomeState()
}

sealed class HomeLoadMoreState {
    object Init : HomeLoadMoreState()
    data class Loading(val isLoading: Boolean = true) : HomeLoadMoreState()
    data class Success(val data: List<MovieEntity>) : HomeLoadMoreState()
    data class Empty(val data: List<MovieEntity> = mutableListOf()) : HomeLoadMoreState()
    data class Error(val data: BasicEntity?) : HomeLoadMoreState()
}
package com.rukantala.movieapp.presentation.genre.bygenre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rukantala.movieapp.domain.entity.BasicEntity
import com.rukantala.movieapp.domain.entity.MovieEntity
import com.rukantala.movieapp.domain.usecase.GenreUseCase
import com.rukantala.movieapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieByGenreViewModel @Inject constructor(
    private val useCase: GenreUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<MovieByGenre>(MovieByGenre.Init)
    val state get() = _state

    private val _lists = MutableStateFlow<List<MovieEntity>?>(mutableListOf())
    val list get() = _lists

    private val _loadMoreState = MutableStateFlow<LoadMoreMovieByGenre>(LoadMoreMovieByGenre.Init)
    val loadMoreState get() = _loadMoreState

    private val _loadMoreLists = MutableStateFlow<List<MovieEntity>?>(mutableListOf())
    val loadMoreList get() = _loadMoreLists

    var page = 1

    fun fetchAllMovieByGenre(genre: String) {
        page = 1
        viewModelScope.launch {
            useCase.fetchMovieByGenre(genre, page.toString())
                .onStart { _state.value = MovieByGenre.Loading(true) }
                .catch { }
                .collect {
                    when (it) {
                        is Result.Success -> {
                            _lists.value = it.data
                            _state.value = MovieByGenre.Success(_lists.value ?: listOf())
                        }
                        is Result.Error -> _state.value =
                            MovieByGenre.Error(it.response.toBasicEntity())
                    }
                }
        }
    }

    fun fetchLoadMoreAllMovieByGenre(genre: String) {
        viewModelScope.launch {
            useCase.fetchMovieByGenre(genre, page.toString())
                .onStart { _loadMoreState.value = LoadMoreMovieByGenre.Loading(true) }
                .catch { }
                .collect {
                    when (it) {
                        is Result.Success -> {
                            _loadMoreLists.value = it.data
                            _loadMoreState.value =
                                LoadMoreMovieByGenre.Success(_loadMoreLists.value ?: listOf())
                        }
                        is Result.Error -> _loadMoreState.value =
                            LoadMoreMovieByGenre.Error(it.response.toBasicEntity())
                    }
                }
        }
    }
}

sealed class MovieByGenre {
    object Init : MovieByGenre()
    data class Loading(val isLoading: Boolean = true) : MovieByGenre()
    data class Success(val data: List<MovieEntity>) : MovieByGenre()
    data class Empty(val data: List<MovieEntity> = mutableListOf()) : MovieByGenre()
    data class Error(val data: BasicEntity?) : MovieByGenre()
}

sealed class LoadMoreMovieByGenre {
    object Init : LoadMoreMovieByGenre()
    data class Loading(val isLoading: Boolean = true) : LoadMoreMovieByGenre()
    data class Success(val data: List<MovieEntity>) : LoadMoreMovieByGenre()
    data class Empty(val data: List<MovieEntity> = mutableListOf()) : LoadMoreMovieByGenre()
    data class Error(val data: BasicEntity?) : LoadMoreMovieByGenre()
}
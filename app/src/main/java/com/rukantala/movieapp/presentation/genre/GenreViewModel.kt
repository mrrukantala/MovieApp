package com.rukantala.movieapp.presentation.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rukantala.movieapp.domain.entity.BasicEntity
import com.rukantala.movieapp.domain.entity.GenreEntity
import com.rukantala.movieapp.domain.usecase.GenreUseCase
import com.rukantala.movieapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(private val useCase: GenreUseCase) : ViewModel() {

    private val _state = MutableStateFlow<GenreState>(GenreState.Init)
    val state get() = _state

    private val _lists = MutableStateFlow<List<GenreEntity>?>(mutableListOf())
    val list get() = _lists

    var page = 1

    fun fetchAllGenre() {
        page = 1
        viewModelScope.launch {
            useCase.fetchAllGenre()
                .onStart { _state.value = GenreState.Loading(true) }
                .catch { }
                .collect {
                    when (it) {
                        is Result.Success -> {
                            _lists.value = it.data
                            _state.value = GenreState.Success(_lists.value ?: listOf())
                        }
                        is Result.Error -> _state.value =
                            GenreState.Error(it.response.toBasicEntity())
                    }
                }
        }
    }

}

sealed class GenreState {
    object Init : GenreState()
    data class Loading(val isLoading: Boolean = true) : GenreState()
    data class Success(val data: List<GenreEntity>) : GenreState()
    data class Empty(val data: List<GenreEntity> = mutableListOf()) : GenreState()
    data class Error(val data: BasicEntity?) : GenreState()

}
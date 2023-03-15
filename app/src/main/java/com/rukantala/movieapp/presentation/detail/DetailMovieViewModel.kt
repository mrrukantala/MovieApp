package com.rukantala.movieapp.presentation.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rukantala.movieapp.domain.entity.BasicEntity
import com.rukantala.movieapp.domain.entity.DetailMovieEntity
import com.rukantala.movieapp.domain.entity.ReviewEntity
import com.rukantala.movieapp.domain.entity.VideoEntity
import com.rukantala.movieapp.domain.usecase.DetailUseCase
import com.rukantala.movieapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val useCase: DetailUseCase
) : ViewModel() {

    private val _stateData = MutableStateFlow<DetailDataState>(DetailDataState.Init)
    val state get() = _stateData

    private val _dataDetail = MutableStateFlow<DetailMovieEntity?>(null)
    val dataDetail get() = _dataDetail

    private val _stateReview = MutableStateFlow<ReviewMovieState>(ReviewMovieState.Init)
    val stateReview get() = _stateReview

    private val _dataReview = MutableStateFlow<List<ReviewEntity>?>(mutableListOf())
    val dataReview get() = _dataReview

    private val _stateVideo = MutableStateFlow<VideoMovieState>(VideoMovieState.Init)
    val stateVideo get() = _stateVideo

    private val _dataVideo = MutableStateFlow<List<VideoEntity>?>(listOf())
    val dataVideo get() = _dataVideo

    var page = 1

    fun fetchDetailData(movieId: String) {
        viewModelScope.launch {

            useCase.fetchDetailMovie(movieId)
                .onStart {
                    _stateData.value = DetailDataState.Loading(true) }
                .catch { }
                .collect {
                    when (it) {
                        is Result.Success -> {
                            _dataDetail.value = it.data
                            _stateData.value = DetailDataState.Success(
                                _dataDetail.value ?: DetailMovieEntity(
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    listOf()
                                )
                            )
                        }
                        is Result.Error -> _stateData.value =
                            DetailDataState.Error(it.response.toBasicEntity())
                    }
                }
        }
    }

    fun fetchReviewData(movieId: String, page: String) {
        viewModelScope.launch {
            useCase.fetchReview(movieId, page)
                .onStart { _stateReview.value = ReviewMovieState.Loading(true) }
                .catch { }
                .collect {
                    when (it) {
                        is Result.Success -> {
                            _dataReview.value = it.data
                            _stateReview.value =
                                ReviewMovieState.Success(_dataReview.value ?: listOf())
                        }
                        is Result.Error -> _stateReview.value =
                            ReviewMovieState.Error(it.response.toBasicEntity())
                    }
                }
        }
    }

    fun fetchVideo(movieId: String) {
        viewModelScope.launch {
            useCase.fetchVideoMovie(movieId)
                .onStart { _stateVideo.value = VideoMovieState.Loading(true) }
                .catch { }
                .collect {
                    when (it) {
                        is Result.Success -> {
                            _dataVideo.value = it.data
                            _stateVideo.value =
                                VideoMovieState.Success(_dataVideo.value ?: listOf())
                        }
                        is Result.Error -> _stateVideo.value =
                            VideoMovieState.Error(it.response.toBasicEntity())
                    }
                }
        }
    }

}


sealed class DetailDataState {
    object Init : DetailDataState()
    data class Loading(val isLoading: Boolean = true) : DetailDataState()
    data class Success(val data: DetailMovieEntity) : DetailDataState()
    data class Empty(val data: DetailMovieEntity) : DetailDataState()
    data class Error(val data: BasicEntity?) : DetailDataState()
}

sealed class ReviewMovieState {
    object Init : ReviewMovieState()
    data class Loading(val isLoading: Boolean = true) : ReviewMovieState()
    data class Success(val data: List<ReviewEntity>) : ReviewMovieState()
    data class Empty(val data: List<ReviewEntity>) : ReviewMovieState()
    data class Error(val data: BasicEntity?) : ReviewMovieState()
}

sealed class VideoMovieState {
    object Init : VideoMovieState()
    data class Loading(val isLoading: Boolean = true) : VideoMovieState()
    data class Success(val data: List<VideoEntity>) : VideoMovieState()
    data class Empty(val data: List<VideoEntity>) : VideoMovieState()
    data class Error(val data: BasicEntity?) : VideoMovieState()
}
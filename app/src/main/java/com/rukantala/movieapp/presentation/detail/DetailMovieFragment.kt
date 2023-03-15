package com.rukantala.movieapp.presentation.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.kennyc.view.MultiStateView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.rukantala.movieapp.R
import com.rukantala.movieapp.databinding.FragmentDetailMovieBinding
import com.rukantala.movieapp.domain.entity.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailMovieFragment : Fragment() {
    lateinit var binding: FragmentDetailMovieBinding
    private val viewModel: DetailMovieViewModel by viewModels()

    private var isRequestingLoadMoreData = false
    private var isLoadMoreFinish = false

    val currentDataReview: MutableList<ReviewEntity> = mutableListOf()

    private val menuNavController: NavController? by lazy {
        activity?.findNavController(R.id.nav_host_fragment_menu)
    }

    private val adapterGenre: GenreAdapter by lazy {
        GenreAdapter()
    }

    private val adapterReview: ReviewAdapter by lazy {
        ReviewAdapter()
    }
    private val args: DetailMovieFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        binding.rvGenre.adapter = adapterGenre
        binding.rvReview.adapter = adapterReview
        binding.apply {
            toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24_black)
            toolbar.title = "Detail Movie"

            toolbar.setNavigationOnClickListener {
                menuNavController?.navigateUp()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        with(binding.nsv) {
            viewTreeObserver.addOnScrollChangedListener {

                val view = getChildAt(childCount - 1)
                if (view.bottom - (height + scrollY) == 0 && !isRequestingLoadMoreData && !isLoadMoreFinish) {
                    fetchLoadMoreReview(args.idMovie)
                }
            }
        }
        observer()
    }

    private fun fetchLoadMoreReview(movieId: String) {
        viewModel.fetchLoadMoreReviewData(movieId)
    }

    private fun observer() {
        fetchDetailMovie(args.idMovie)
        fetchReviewMovie(args.idMovie)
        fetchVideoMovie(args.idMovie)

        viewModel.state.flowWithLifecycle(lifecycle).onEach { handleStateData(it) }
            .launchIn(lifecycleScope)
        viewModel.stateReview.flowWithLifecycle(lifecycle).onEach { handleStateReview(it) }
            .launchIn(lifecycleScope)
        viewModel.loadMoreStateReview.flowWithLifecycle(lifecycle)
            .onEach { handleLoadMoreStateReview(it) }.launchIn(lifecycleScope)
        viewModel.stateVideo.flowWithLifecycle(lifecycle).onEach { handleStateVideo(it) }
            .launchIn(lifecycleScope)
    }

    private fun handleLoadMoreStateReview(state: LoadMoreReviewMovieState) {
        when (state) {
            is LoadMoreReviewMovieState.Loading -> fetchLoadMoreReviewMovieOnLoading()
            is LoadMoreReviewMovieState.Success -> {
                fetchLoadMoreReviewMovieOnSuccess(state.data)
                viewModel.page++
            }
            is LoadMoreReviewMovieState.Empty -> fetchLoadMoreReviewMovieOnEmpty()
            is LoadMoreReviewMovieState.Error -> fetchLoadMoreReviewMovieOnError(state.data)
            else -> {}
        }
    }

    private fun fetchLoadMoreReviewMovieOnError(data: BasicEntity?) {
        Toast.makeText(requireContext(), data?.message ?: "", Toast.LENGTH_SHORT).show()
    }

    private fun fetchLoadMoreReviewMovieOnEmpty() {

    }

    private fun fetchLoadMoreReviewMovieOnSuccess(data: List<ReviewEntity>) {
        if (data.isNotEmpty()) {
            currentDataReview.addAll(data)
            adapterReview.notifyDataSetChanged()
            adapterReview.submitList(currentDataReview)
        }
    }

    private fun fetchLoadMoreReviewMovieOnLoading() {
        Toast.makeText(requireContext(), getString(R.string.lbl_loading), Toast.LENGTH_SHORT)
            .show()
    }

    private fun fetchDetailMovie(movieId: String) {
        viewModel.fetchDetailData(movieId)
    }

    private fun fetchDetailMoviewOnLoading() {
        binding.msvDetail.viewState = MultiStateView.ViewState.LOADING
    }

    @SuppressLint("SetTextI18n")
    private fun fetchDetailMoviewOnSuccess(data: DetailMovieEntity) {
        val adapter = binding.rvGenre.adapter as GenreAdapter
        adapter.submitList(data.genres)

        binding.msvDetail.viewState = MultiStateView.ViewState.CONTENT
        binding.apply {
            detailItem = data
            tvRate.text = "${data.voteAverage}/10.0 IMDb"

        }
    }

    private fun fetchDetailMoviewOnEmpty() {
        binding.msvDetail.viewState = MultiStateView.ViewState.EMPTY
    }

    private fun fetchDetailMoviewOnError(data: BasicEntity?) {
        binding.msvDetail.viewState = MultiStateView.ViewState.ERROR
    }

    private fun handleStateData(state: DetailDataState) {
        when (state) {
            is DetailDataState.Loading -> fetchDetailMoviewOnLoading()
            is DetailDataState.Success -> fetchDetailMoviewOnSuccess(state.data)
            is DetailDataState.Empty -> fetchDetailMoviewOnEmpty()
            is DetailDataState.Error -> fetchDetailMoviewOnError(state.data)
            else -> {}
        }
    }

    private fun fetchReviewMovie(idMovie: String) {
        viewModel.fetchReviewData(idMovie)
    }

    private fun fetchReviewMovieOnLoading() {

    }

    private fun fetchReviewMovieOnSuccess(data: List<ReviewEntity>) {
        currentDataReview.addAll(data)
        adapterReview.submitList(data)

        binding.msvReview.viewState = MultiStateView.ViewState.CONTENT
    }

    private fun fetchReviewMovieOnEmpty() {

    }

    private fun fetchReviewMovieOnError(data: BasicEntity?) {
        Toast.makeText(requireContext(), data?.message.toString() ?: "", Toast.LENGTH_SHORT)
            .show()
    }


    private fun handleStateReview(state: ReviewMovieState) {
        when (state) {
            is ReviewMovieState.Loading -> fetchReviewMovieOnLoading()
            is ReviewMovieState.Success -> {
                fetchReviewMovieOnSuccess(state.data)
                viewModel.page++
            }
            is ReviewMovieState.Empty -> fetchReviewMovieOnEmpty()
            is ReviewMovieState.Error -> fetchReviewMovieOnError(state.data)
            else -> {}
        }
    }

    private fun fetchVideoMovie(idMovie: String) {
        viewModel.fetchVideo(idMovie)
    }

    private fun fetchVideoMovieOnLoading() {

    }

    private fun fetchVideoMovieOnSuccess(data: List<VideoEntity>) {
        val data: YouTubePlayerListener = object : YouTubePlayerListener {
            override fun onApiChange(youTubePlayer: YouTubePlayer) {

            }

            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {

            }

            override fun onError(
                youTubePlayer: YouTubePlayer,
                error: PlayerConstants.PlayerError
            ) {

            }

            override fun onPlaybackQualityChange(
                youTubePlayer: YouTubePlayer, playbackQuality: PlayerConstants.PlaybackQuality
            ) {

            }

            override fun onPlaybackRateChange(
                youTubePlayer: YouTubePlayer, playbackRate: PlayerConstants.PlaybackRate
            ) {

            }

            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(data[0].key, 0F)
                youTubePlayer.pause()
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState
            ) {

            }

            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {

            }

            override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
                youTubePlayer.pause()

            }

            override fun onVideoLoadedFraction(
                youTubePlayer: YouTubePlayer, loadedFraction: Float
            ) {

            }

        }
        binding.youtubePlayerView.addYouTubePlayerListener(data)
    }

    private fun fetchVideoMovieOnEmpty() {

    }

    private fun fetchVideoMovieOnError(data: BasicEntity?) {

    }


    private fun handleStateVideo(state: VideoMovieState) {
        when (state) {
            is VideoMovieState.Loading -> fetchVideoMovieOnLoading()
            is VideoMovieState.Success -> fetchVideoMovieOnSuccess(state.data)
            is VideoMovieState.Empty -> fetchVideoMovieOnEmpty()
            is VideoMovieState.Error -> fetchVideoMovieOnError(state.data)
            else -> {}
        }
    }
}
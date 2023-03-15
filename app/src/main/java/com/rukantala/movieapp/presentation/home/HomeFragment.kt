package com.rukantala.movieapp.presentation.home

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
import com.kennyc.view.MultiStateView
import com.rukantala.movieapp.R
import com.rukantala.movieapp.databinding.FragmentHomeBinding
import com.rukantala.movieapp.domain.entity.BasicEntity
import com.rukantala.movieapp.domain.entity.MovieEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    private var isRequestingLoadMoreData = false
    private var isLoadMoreFinish = false

    val currentData: MutableList<MovieEntity> = mutableListOf()

    private val menuNavController: NavController? by lazy {
        activity?.findNavController(R.id.nav_host_fragment_menu)
    }

    private val adapter: MovieAdapter by lazy {
        MovieAdapter(MovieAdapter.OnclickListener {
            menuNavController?.navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(
                    it.id
                )
            )
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.rvMovie.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        with(binding.nsv) {
            viewTreeObserver.addOnScrollChangedListener {

                val view = getChildAt(childCount - 1)
                if (view.bottom - (height + scrollY) == 0 && !isRequestingLoadMoreData && !isLoadMoreFinish) {
                    fetchLoadMoreMovie()
                }
            }
        }
        observer()
    }

    private fun observer() {
        fetchAllMovie()

        viewModel.state.flowWithLifecycle(lifecycle)
            .onEach { handleStateHome(it) }
            .launchIn(lifecycleScope)

        viewModel.stateLoadMore.flowWithLifecycle(lifecycle)
            .onEach { handleStateLoadMoreHome(it) }
            .launchIn(lifecycleScope)
    }

    private fun handleStateLoadMoreHome(state: HomeLoadMoreState) {
        when (state) {
            is HomeLoadMoreState.Loading -> {
                movieLoadMoreOnLoading()
            }
            is HomeLoadMoreState.Success -> {
                movieLoadMoreOnSuccess(state.data)
                viewModel.page++
            }
            is HomeLoadMoreState.Empty -> movieLoadMoreOnEmpty()
            is HomeLoadMoreState.Error -> movieLoadMoreOnError(state.data)
            else -> {}
        }
    }

    private fun handleStateHome(state: HomeState) {
        when (state) {
            is HomeState.Loading -> movieOnLoading()
            is HomeState.Success -> {
                movieOnSuccess(state.data)
                viewModel.page++
            }
            is HomeState.Empty -> movieOnEmpty(state.data)
            is HomeState.Error -> movieOnError(state.data)
            else -> {}
        }
    }

    private fun fetchAllMovie() {
        viewModel.fetchAllMovie()
    }

    private fun movieOnLoading() {
        binding.msvMovie.viewState = MultiStateView.ViewState.LOADING
    }

    private fun movieOnSuccess(data: List<MovieEntity>) {
        currentData.addAll(data)
        adapter.submitList(currentData)

        binding.msvMovie.viewState =
            if (data.isEmpty()) MultiStateView.ViewState.EMPTY else MultiStateView.ViewState.CONTENT

        isRequestingLoadMoreData = false
    }

    private fun movieOnEmpty(data: List<MovieEntity>) {
        binding.msvMovie.viewState = MultiStateView.ViewState.EMPTY
        adapter.submitList(data)
    }

    private fun movieOnError(e: BasicEntity?) {
        Toast.makeText(requireContext(), e?.message.toString() ?: "", Toast.LENGTH_SHORT)
            .show()
    }

    private fun fetchLoadMoreMovie() {
        isRequestingLoadMoreData = true
        viewModel.fetchLoadMoreMovie()
    }

    private fun movieLoadMoreOnLoading() {
        Toast.makeText(requireContext(), getString(R.string.lbl_loading), Toast.LENGTH_SHORT).show()
    }

    private fun movieLoadMoreOnSuccess(data: List<MovieEntity>) {
        if (data.isNotEmpty()) {
            currentData.addAll(data)
            adapter.notifyDataSetChanged()
            adapter.submitList(currentData)
        }
        isRequestingLoadMoreData = false
    }

    private fun movieLoadMoreOnEmpty() {
        isRequestingLoadMoreData = false
    }

    private fun movieLoadMoreOnError(e: BasicEntity?) {
        isRequestingLoadMoreData = false
    }

}
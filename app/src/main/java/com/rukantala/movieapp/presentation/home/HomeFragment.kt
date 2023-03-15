package com.rukantala.movieapp.presentation.home

import android.os.Bundle
import android.util.Log
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
        observer()
    }

    private fun observer() {
        fetchAllMovie()

        viewModel.state.flowWithLifecycle(lifecycle)
            .onEach { handleStateHome(it) }
            .launchIn(lifecycleScope)
    }

    private fun handleStateHome(state: HomeState) {
        when (state) {
            is HomeState.Loading -> movieOnLoading()
            is HomeState.Success -> movieOnSuccess(state.data)
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
        val adapter = binding.rvMovie.adapter as MovieAdapter
        adapter.submitList(data)

        binding.msvMovie.viewState =
            if (data.isEmpty()) MultiStateView.ViewState.EMPTY else MultiStateView.ViewState.CONTENT
        adapter.submitList(data)
    }

    private fun movieOnEmpty(data: List<MovieEntity>) {
        binding.msvMovie.viewState = MultiStateView.ViewState.EMPTY
        adapter.submitList(data)
    }

    private fun movieOnError(e: BasicEntity?) {
        Toast.makeText(requireContext(), "terjadi gangguan mohon coba kembali", Toast.LENGTH_SHORT)
            .show()
    }

    private fun fetchLoadMoreMovie() {

    }

    private fun movieLoadMoreOnLoading() {

    }

    private fun movieLoadMoreOnSuccess(data: List<MovieEntity>) {

    }

    private fun movieLoadMoreOnEmpty() {

    }

    private fun movieLoadMoreOnError(e: BasicEntity?) {

    }

}
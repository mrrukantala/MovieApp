package com.rukantala.movieapp.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

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
        Log.v("DATA", "GET")
        viewModel.fetchAllMovie()
    }

    private fun movieOnLoading() {
        Log.v("DATA", "LOADING")
    }

    private fun movieOnSuccess(data: List<MovieEntity>) {
        Log.v("DATA", "SUKSES")
    }

    private fun movieOnEmpty(data: List<MovieEntity>) {
        Log.v("DATA", "KOSONG")

    }

    private fun movieOnError(e: BasicEntity?) {
        Log.v("DATA", "ERROR")

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
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
import com.rukantala.movieapp.domain.entity.BasicEntity
import com.rukantala.movieapp.domain.entity.MovieEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : Fragment(), HomeDataContract {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.flowWithLifecycle(lifecycle)
            .onEach { state -> HomeObserver(this, viewModel, state) }
            .launchIn(lifecycleScope)
    }

    override fun fetchAllMovie() {
        Log.v("DATA", "GET")
        viewModel.fetchAllMovie()
    }

    override fun movieOnLoading() {
        Log.v("DATA", "LOADING")
        TODO("Not yet implemented")
    }

    override fun movieOnSuccess(data: List<MovieEntity>) {
        Log.v("DATA", "SUKSES")
        TODO("Not yet implemented")
    }

    override fun movieOnEmpty(data: List<MovieEntity>) {
        Log.v("DATA", "KOSONG")
        TODO("Not yet implemented")
    }

    override fun movieOnError(e: BasicEntity?) {
        Log.v("DATA", "ERROR")
        TODO("Not yet implemented")
    }

    override fun fetchLoadMoreMovie() {
        TODO("Not yet implemented")
    }

    override fun movieLoadMoreOnLoading() {
        TODO("Not yet implemented")
    }

    override fun movieLoadMoreOnSuccess(data: List<MovieEntity>) {
        TODO("Not yet implemented")
    }

    override fun movieLoadMoreOnEmpty() {
        TODO("Not yet implemented")
    }

    override fun movieLoadMoreOnError(e: BasicEntity?) {
        TODO("Not yet implemented")
    }

}
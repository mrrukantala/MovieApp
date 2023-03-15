package com.rukantala.movieapp.presentation.genre.bygenre

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
import androidx.navigation.fragment.navArgs
import com.kennyc.view.MultiStateView
import com.rukantala.movieapp.R
import com.rukantala.movieapp.databinding.FragmentMovieByGenreBinding
import com.rukantala.movieapp.domain.entity.BasicEntity
import com.rukantala.movieapp.domain.entity.MovieEntity
import com.rukantala.movieapp.presentation.home.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MovieByGenreFragment : Fragment() {
    lateinit var binding: FragmentMovieByGenreBinding
    private val viewModel: MovieByGenreViewModel by viewModels()
    private val args: MovieByGenreFragmentArgs by navArgs()

    private val menuNavController: NavController? by lazy {
        activity?.findNavController(R.id.nav_host_fragment_menu)
    }

    private val adapter: MovieAdapter by lazy {
        MovieAdapter(MovieAdapter.OnclickListener {
            menuNavController?.navigate(MovieByGenreFragmentDirections.actionMovieByGenreFragmentToDetailMovieFragment(
                it.id
            ))
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMovieByGenreBinding.inflate(inflater, container, false)
        binding.rvMovieByGenre.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        observer()
    }

    private fun observer() {
        fetchAllMovieByGenre(args.idGenre)

        viewModel.state.flowWithLifecycle(lifecycle)
            .onEach { handleStateHome(it) }
            .launchIn(lifecycleScope)
    }

    private fun handleStateHome(state: MovieByGenre) {
        when (state) {
            is MovieByGenre.Loading -> movieOnLoading()
            is MovieByGenre.Success -> movieOnSuccess(state.data)
            is MovieByGenre.Empty -> movieOnEmpty(state.data)
            is MovieByGenre.Error -> movieOnError(state.data)
            else -> {}
        }
    }

    private fun fetchAllMovieByGenre(genre: String) {
        viewModel.fetchAllMovieByGenre(genre)
    }

    private fun movieOnLoading() {
        binding.msvMovieByGenre.viewState = MultiStateView.ViewState.LOADING
    }

    private fun movieOnSuccess(data: List<MovieEntity>) {
        Log.v("DATA", data.toString())
        val adapter = binding.rvMovieByGenre.adapter as MovieAdapter
        adapter.submitList(data)

        binding.msvMovieByGenre.viewState =
            if (data.isEmpty()) MultiStateView.ViewState.EMPTY else MultiStateView.ViewState.CONTENT
        adapter.submitList(data)
    }

    private fun movieOnEmpty(data: List<MovieEntity>) {
        binding.msvMovieByGenre.viewState = MultiStateView.ViewState.EMPTY
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
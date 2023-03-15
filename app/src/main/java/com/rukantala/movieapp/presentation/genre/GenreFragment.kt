package com.rukantala.movieapp.presentation.genre

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
import com.rukantala.movieapp.databinding.FragmentGenreBinding
import com.rukantala.movieapp.domain.entity.BasicEntity
import com.rukantala.movieapp.domain.entity.GenreEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GenreFragment : Fragment() {
    lateinit var binding: FragmentGenreBinding
    private val viewModel: GenreViewModel by viewModels()

    private val menuNavController: NavController? by lazy {
        activity?.findNavController(R.id.nav_host_fragment_menu)
    }

    private val adapter: GenreAdapter by lazy {
        GenreAdapter(GenreAdapter.OnclickListener {
            menuNavController?.navigate(
                GenreFragmentDirections.actionGenreFragmentToMovieByGenreFragment(
                    it.id.toString()
                )
            )
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGenreBinding.inflate(inflater, container, false)
        binding.rvGenre.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        observer()
    }

    private fun observer() {
        fetchAllGenre()

        viewModel.state.flowWithLifecycle(lifecycle)
            .onEach { handleStateGenre(it) }
            .launchIn(lifecycleScope)
    }

    private fun handleStateGenre(state: GenreState) {
        when (state) {
            is GenreState.Loading -> movieOnLoading()
            is GenreState.Success -> movieOnSuccess(state.data)
            is GenreState.Empty -> movieOnEmpty(state.data)
            is GenreState.Error -> movieOnError(state.data)
            else -> {}
        }
    }

    private fun fetchAllGenre() {
        viewModel.fetchAllGenre()
    }

    private fun movieOnLoading() {
        binding.msvGenre.viewState = MultiStateView.ViewState.LOADING
    }

    private fun movieOnSuccess(data: List<GenreEntity>) {
        val adapter = binding.rvGenre.adapter as GenreAdapter
        adapter.submitList(data)

        binding.msvGenre.viewState =
            if (data.isEmpty()) MultiStateView.ViewState.EMPTY else MultiStateView.ViewState.CONTENT
        adapter.submitList(data)
    }

    private fun movieOnEmpty(data: List<GenreEntity>) {
        binding.msvGenre.viewState = MultiStateView.ViewState.EMPTY
        adapter.submitList(data)
    }

    private fun movieOnError(e: BasicEntity?) {
        Toast.makeText(requireContext(), e?.message ?: "", Toast.LENGTH_SHORT)
            .show()
    }
}
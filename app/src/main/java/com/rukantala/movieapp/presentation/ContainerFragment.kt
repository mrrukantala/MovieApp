package com.rukantala.movieapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rukantala.movieapp.R
import com.rukantala.movieapp.databinding.FragmentContainerBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContainerFragment : Fragment() {
    private lateinit var binding: FragmentContainerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        val nav = childFragmentManager.findFragmentById(
            R.id.nav_host_fragment_menu
        ) as NavHostFragment

        inflate(nav, binding.bnMenu)
    }

    private fun inflate(nav: NavHostFragment, menu: BottomNavigationView) {
        NavigationUI.setupWithNavController(menu, nav.navController)
        nav.navController.addOnDestinationChangedListener { _, main, _ ->
            when (main.id) {
                R.id.homeFragment, R.id.genreFragment -> {
                    showBottomNav(menu = menu)
                }
                else -> {
                    hideBottomNav(menu = menu)
                }
            }
        }
    }

    fun showBottomNav(duration: Int = 400, menu: BottomNavigationView) {
        if (menu.visibility == View.VISIBLE) return
        menu.visibility = View.VISIBLE
        val animate = TranslateAnimation(0f, 0f, menu.height.toFloat(), 0f)
        animate.duration = duration.toLong()
        menu.startAnimation(animate)
    }

    fun hideBottomNav(duration: Int = 400, menu: BottomNavigationView) {
        if (menu.visibility == View.GONE) return
        val animate = TranslateAnimation(0f, 0f, 0f, menu.height.toFloat())
        animate.duration = duration.toLong()
        menu.startAnimation(animate)
        menu.visibility = View.GONE
    }
}
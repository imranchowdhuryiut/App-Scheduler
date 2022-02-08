package com.imran.demo.movie.list.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.imran.demo.movie.list.R
import com.imran.demo.movie.list.databinding.FragmentSplashScreenBinding
import kotlinx.coroutines.delay

class SplashScreenFragment : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenResumed {
            delay(2000)
            findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToMovieListFragment())
        }
    }
}
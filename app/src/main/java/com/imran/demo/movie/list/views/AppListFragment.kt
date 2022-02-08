package com.imran.demo.movie.list.views

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imran.demo.movie.list.AppScheduler
import com.imran.demo.movie.list.R
import com.imran.demo.movie.list.databinding.FragmentAppListBinding
import com.imran.demo.movie.list.viewmodels.AppViewModel
import com.imran.demo.movie.list.views.adapters.ScheduledAppListAdapter

class AppListFragment : Fragment() {

    private var _binding: FragmentAppListBinding? = null

    private val mViewModel by viewModels<AppViewModel>()

    private val mAdapter = ScheduledAppListAdapter()

    private val askPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionMap ->
            for (permission in permissionMap) {
                Log.d(AppScheduler.tag, permission.key + " granted " + permission.value)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAppListBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getAppData()
        observeAppData()
    }

    private fun getAppData() {
        mViewModel.getAllSavedAppList()
    }

    private fun observeAppData() {
        mViewModel.appList.observe(viewLifecycleOwner, {
            mAdapter.submitList(it)
        })
    }

    private fun initView() {
        _binding?.apply {
            layoutCustomToolbar.tvToolbarTitle.text = getString(R.string.movies)
            layoutCustomToolbar.btnBack.visibility = View.GONE
            layoutCustomToolbar.btnAdd.setOnClickListener {
                //checkAndAskPermission()
                goToAppSelectScreen()
            }
            rvAppList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            rvAppList.adapter = mAdapter
        }
    }

    private fun checkAndAskPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.QUERY_ALL_PACKAGES
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                askPermissionResult.launch(
                    arrayOf(
                        Manifest.permission.QUERY_ALL_PACKAGES,
                        Manifest.permission.INTERNET
                    )
                )
            } else {
                askPermissionResult.launch(
                    arrayOf(
                        Manifest.permission.INTERNET
                    )
                )
            }
        } else {
            goToAppSelectScreen()
        }
    }

    private fun goToAppSelectScreen() {
        findNavController().navigate(
            AppListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment()
        )
    }

}
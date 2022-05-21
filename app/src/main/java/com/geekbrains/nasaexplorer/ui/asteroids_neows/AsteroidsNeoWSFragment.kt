package com.geekbrains.nasaexplorer.ui.asteroids_neows

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.geekbrains.nasaexplorer.R
import com.geekbrains.nasaexplorer.databinding.FragmentAsteroidsneowsBinding
import com.geekbrains.nasaexplorer.domain.NasaRepositoryImpl
import com.geekbrains.nasaexplorer.utils.hide
import com.geekbrains.nasaexplorer.utils.show

class AsteroidsNeoWSFragment : Fragment(R.layout.fragment_asteroidsneows) {

    private val asteroidsNeoWSViewModel: AsteroidsNeoWSViewModel by viewModels {
        AsteroidsNeoWSViewModelFactory(NasaRepositoryImpl())
    }

    private val adapter = AsteroidsNeoWSFragmentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        asteroidsNeoWSViewModel.requestAsteroidsInfo()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAsteroidsneowsBinding.bind(view)
        binding.asteroidsneowsFragmentRecyclerView.adapter = adapter

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            asteroidsNeoWSViewModel.loading.collect { isLoading ->
                when (isLoading) {
                    true -> showProgress(binding)
                    false -> showContent(binding)
                }
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            asteroidsNeoWSViewModel.error.collect { errorMessage ->
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            asteroidsNeoWSViewModel.asteroidsNeoWSFragmentDataset.collect { dataset ->
                adapter.setAsteroid(dataset)
            }
        }
    }

    private fun showProgress(binding: FragmentAsteroidsneowsBinding) {
        with(binding) {
            progressBarIncluded.progressBarRoot.show()
            asteroidsneowsFragmentDataContainer.hide()
        }
    }

    private fun showContent(binding: FragmentAsteroidsneowsBinding) {
        with(binding) {
            asteroidsneowsFragmentDataContainer.show()
            progressBarIncluded.progressBarRoot.hide()
        }
    }
}
package com.geekbrains.nasaexplorer.ui.asteroids_neows

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.geekbrains.nasaexplorer.R
import com.geekbrains.nasaexplorer.databinding.FragmentAsteroidsneowsBinding
import com.geekbrains.nasaexplorer.domain.NasaRepositoryImpl
import com.geekbrains.nasaexplorer.utils.hide
import com.geekbrains.nasaexplorer.utils.makeSnackbar
import com.geekbrains.nasaexplorer.utils.show

private const val HEADER_ANIMATION_TYPE = "rotation"
private const val HEADER_ROTATION_ANGLE = 360f
private const val IMAGE_FINAL_ALPHA = 1f
private const val IMAGE_ANIMATION_DURATION = 1000L
private const val RECYCLER_VIEW_INITIAL_ANGLE = 90f
private const val RECYCLER_VIEW_FINAL_ANGLE = 0f
private const val RECYCLER_VIEW_ANIMATION_TYPE = "rotation"

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
                binding.root.makeSnackbar(
                    text = errorMessage,
                    actionText = getString(R.string.reload),
                    action = {
                        asteroidsNeoWSViewModel.requestAsteroidsInfo()
                    }
                )
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
            asteroidsneowsFragmentCollapsingToolbarImage.hide()
        }
    }

    private fun showContent(binding: FragmentAsteroidsneowsBinding) {
        with(binding) {
            asteroidsneowsFragmentDataContainer.show()
            asteroidsneowsFragmentCollapsingToolbarImage.show()
            progressBarIncluded.progressBarRoot.hide()
            asteroidsneowsFragmentCollapsingToolbarImage.animate().apply {
                alpha(IMAGE_FINAL_ALPHA)
                duration = IMAGE_ANIMATION_DURATION
            }
            ObjectAnimator.ofFloat(
                binding.asteroidsneowsFragmentRecyclerView, RECYCLER_VIEW_ANIMATION_TYPE,
                RECYCLER_VIEW_INITIAL_ANGLE, RECYCLER_VIEW_FINAL_ANGLE
            ).start()
            ObjectAnimator.ofFloat(
                binding.asteroidsneowsFragmentHeader, HEADER_ANIMATION_TYPE,
                HEADER_ROTATION_ANGLE
            ).start()
        }
    }
}
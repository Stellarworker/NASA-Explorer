package com.stellarworker.nasaexplorer.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.load
import com.stellarworker.nasaexplorer.R
import com.stellarworker.nasaexplorer.databinding.FragmentMainBinding
import com.stellarworker.nasaexplorer.domain.NasaRepositoryImpl
import com.stellarworker.nasaexplorer.utils.hide
import com.stellarworker.nasaexplorer.utils.hideKeyboard
import com.stellarworker.nasaexplorer.utils.makeSnackbar
import com.stellarworker.nasaexplorer.utils.show

private const val EMPTY_STRING = ""
private const val BASE_WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/%1\$s"
private const val ANIMATION_DURATION = 1000L

class MainFragment : Fragment(R.layout.fragment_main) {

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(NasaRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.requestPictureOfTheDay()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view)

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            mainViewModel.loading.collect { isLoading ->
                when (isLoading) {
                    true -> showProgress(binding)
                    false -> showContent(binding)
                }
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            mainViewModel.error.collect { errorMessage ->
                binding.root.makeSnackbar(
                    text = errorMessage,
                    actionText = getString(R.string.reload),
                    action = {
                        mainViewModel.requestPictureOfTheDay()
                    }
                )
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            mainViewModel.mainFragmentDataset.collect { dataset ->
                with(binding) {
                    mainFragmentPictureOfTheDay.load(dataset.image)
                    bottomSheetIncluded.bottomSheetPictureTitle.text =
                        dataset.title
                    bottomSheetIncluded.bottomSheetPictureExplanation.text =
                        dataset.explanation
                }
            }
        }

        binding.bottomSheetIncluded.bottomSheetSearchButton.setOnClickListener {
            with(binding) {
                root.hideKeyboard()
                bottomSheetIncluded.bottomSheetWikipediaTextInput
                    .editText?.text.let { text ->
                        when (val query = text.toString().trim().lowercase()) {
                            EMPTY_STRING -> root.makeSnackbar(getString(R.string.empty_query))
                            else -> openWikipedia(query)
                        }
                    }
            }
        }
    }

    private fun openWikipedia(query: String) {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(String.format(BASE_WIKIPEDIA_URL, query))
        })
    }

    private fun showProgress(binding: FragmentMainBinding) {
        with(binding) {
            progressBarIncluded.progressBarRoot.show()
            bottomSheetIncluded.bottomSheetRoot.hide()
            mainFragmentDataContainer.hide()
        }
    }

    private fun showContent(binding: FragmentMainBinding) {
        with(binding) {
            mainFragmentDataContainer.show()
            TransitionManager.beginDelayedTransition(
                mainFragmentRoot,
                TransitionSet().apply {
                    addTransition(Slide(Gravity.TOP))
                    addTransition(Fade())
                    duration = ANIMATION_DURATION
                    ordering = TransitionSet.ORDERING_TOGETHER
                })
            mainFragmentHeader.show()
            mainFragmentPictureOfTheDay.show()
            bottomSheetIncluded.bottomSheetRoot.show()
            progressBarIncluded.progressBarRoot.hide()
        }
    }
}
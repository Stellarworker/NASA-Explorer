package com.geekbrains.nasaexplorer.ui

import android.animation.ValueAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import coil.load
import com.geekbrains.nasaexplorer.R
import com.geekbrains.nasaexplorer.databinding.FragmentMainBinding
import com.geekbrains.nasaexplorer.domain.NasaRepositoryImpl
import com.geekbrains.nasaexplorer.utils.hide
import com.geekbrains.nasaexplorer.utils.hideKeyboard
import com.geekbrains.nasaexplorer.utils.makeSnackbar
import com.geekbrains.nasaexplorer.utils.show

private const val EMPTY_STRING = ""
private const val BASE_WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/%1\$s"
private const val INITIAL_SIZE = 300f
private const val DURATION = 500L

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
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
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
            bottomSheetIncluded.bottomSheetRoot.show()
            progressBarIncluded.progressBarRoot.hide()
        }
        ValueAnimator.ofFloat(INITIAL_SIZE, resources.getDimension(R.dimen.common_header_small))
            .apply {
                duration = DURATION
                addUpdateListener {
                    binding.mainFragmentHeader.textSize = it.animatedValue as Float
                }
            }.start()
    }

}
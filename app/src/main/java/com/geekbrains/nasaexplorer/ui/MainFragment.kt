package com.geekbrains.nasaexplorer.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.view.View
import android.widget.Toast
import androidx.lifecycle.coroutineScope
import coil.load
import com.geekbrains.nasaexplorer.R
import com.geekbrains.nasaexplorer.common.BASE_WIKIPEDIA_URL
import com.geekbrains.nasaexplorer.common.EMPTY_STRING
import com.geekbrains.nasaexplorer.databinding.FragmentMainBinding
import com.geekbrains.nasaexplorer.domain.NasaRepositoryImpl
import com.geekbrains.nasaexplorer.utils.hide
import com.geekbrains.nasaexplorer.utils.hideKeyboard
import com.geekbrains.nasaexplorer.utils.makeSnackbar
import com.geekbrains.nasaexplorer.utils.show

class MainFragment : Fragment(R.layout.fragment_main) {

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(NasaRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            TODO("Not implemented yet!")
        } ?: run {
            mainViewModel.requestPictureOfTheDay()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view)

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            mainViewModel.loading.collect {
                if (it) {
                    binding.progress.show()
                    binding.mainFragmentDataContainer.hide()
                } else {
                    binding.progress.hide()
                    binding.mainFragmentDataContainer.show()
                }
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            mainViewModel.error.collect {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            mainViewModel.image.collect { url ->
                url?.let {
                    binding.mainFragmentPictureOfTheDay.load(it)
                }
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            mainViewModel.title.collect { title ->
                title?.let {
                    binding.mainFragmentPictureTitle.text = it
                }
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            mainViewModel.explanation.collect { explanation ->
                explanation?.let {
                    binding.mainFragmentPictureDescription.text = it
                }
            }
        }

        binding.mainFragmentSearchButton.setOnClickListener {
            with(binding) {
                root.hideKeyboard()
                mainFragmentWikipediaTextInput.editText?.text.let { text ->
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

}
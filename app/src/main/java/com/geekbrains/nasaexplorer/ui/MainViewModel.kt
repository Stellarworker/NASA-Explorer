package com.geekbrains.nasaexplorer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.geekbrains.nasaexplorer.domain.NasaRepository
import com.geekbrains.nasaexplorer.utils.convertPODTItoMainFD
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

private const val NETWORK_ERROR_MESSAGE = "Network Error!"

class MainViewModel(private val repository: NasaRepository) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: Flow<Boolean> = _loading

    private val _mainFragmentDataset: MutableStateFlow<MainFragmentDataset> =
        MutableStateFlow(MainFragmentDataset())
    val mainFragmentDataset: Flow<MainFragmentDataset> = _mainFragmentDataset

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: Flow<String> = _error
    fun requestPictureOfTheDay() {
        _loading.value = true
        viewModelScope.launch {
            try {
                _mainFragmentDataset.emit(convertPODTItoMainFD(repository.pictureOfTheDay()))
            } catch (exc: IOException) {
                _error.emit(NETWORK_ERROR_MESSAGE)
            }
            _loading.emit(false)
        }
    }
}

class MainViewModelFactory(private val repository: NasaRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MainViewModel(repository) as T

}
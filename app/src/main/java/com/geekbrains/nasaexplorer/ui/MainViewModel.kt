package com.geekbrains.nasaexplorer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.geekbrains.nasaexplorer.common.NETWORK_ERROR_MESSAGE
import com.geekbrains.nasaexplorer.domain.NasaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(private val repository: NasaRepository) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: Flow<Boolean> = _loading

    private val _image: MutableStateFlow<String?> = MutableStateFlow(null)
    val image: Flow<String?> = _image

    private val _title: MutableStateFlow<String?> = MutableStateFlow(null)
    val title: Flow<String?> = _title

    private val _explanation: MutableStateFlow<String?> = MutableStateFlow(null)
    val explanation: Flow<String?> = _explanation

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: Flow<String> = _error

    fun requestPictureOfTheDay() {

        _loading.value = true

        viewModelScope.launch {

            try {
                with(repository.pictureOfTheDay()) {
                    _image.emit(url)
                    _title.emit(title)
                    _explanation.emit("\t\t$explanation")
                }

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
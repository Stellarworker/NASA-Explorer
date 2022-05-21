package com.geekbrains.nasaexplorer.ui.asteroids_neows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.geekbrains.nasaexplorer.common.NETWORK_ERROR_MESSAGE
import com.geekbrains.nasaexplorer.domain.NasaRepository
import com.geekbrains.nasaexplorer.utils.makeAsteroidsFragmentDataset
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class AsteroidsNeoWSViewModel(private val repository: NasaRepository) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: Flow<Boolean> = _loading

    private val _asteroidsNeoWSFragmentDataset: MutableStateFlow<AsteroidsNeoWSFragmentDataset> =
        MutableStateFlow(AsteroidsNeoWSFragmentDataset())
    val asteroidsNeoWSFragmentDataset: Flow<AsteroidsNeoWSFragmentDataset> =
        _asteroidsNeoWSFragmentDataset

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: Flow<String> = _error
    fun requestAsteroidsInfo() {
        _loading.value = true
        viewModelScope.launch {
            try {
                _asteroidsNeoWSFragmentDataset.emit(
                    makeAsteroidsFragmentDataset(repository.asteroidsInfo())
                )
            } catch (exc: IOException) {
                _error.emit(NETWORK_ERROR_MESSAGE)
            }
            _loading.emit(false)
        }
    }
}

class AsteroidsNeoWSViewModelFactory(private val repository: NasaRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        AsteroidsNeoWSViewModel(repository) as T

}
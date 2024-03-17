package com.kodeco.android.countryinfo.ui.countryinfo

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.repositories.CountryRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CountryInfoViewModel(private val repository: CountryRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<CountryInfoState> =
        MutableStateFlow(CountryInfoState.Loading)
    val uiState: StateFlow<CountryInfoState> = _uiState

    fun fetchCountries() {
        viewModelScope.launch {
            _uiState.value = CountryInfoState.Loading
                repository.fetchCountries()
                    .catch { error ->
                        _uiState.value = CountryInfoState.Error(error)
                    }
                    .collect { list ->
                        _uiState.value = CountryInfoState.Success(list)
                    }
            }
    }

    var countryRowCounter = mutableIntStateOf(0)
    fun incrementCountryRowCounter() {
        countryRowCounter.intValue++
    }

    var backCounter = mutableIntStateOf(0)
    fun incrementBackCounter() {
        backCounter.intValue++
    }

    var counter = mutableIntStateOf(0)
    private fun incrementCounter() {
        counter.intValue++
    }

    init {
        fetchCountries()
        viewModelScope.launch {
            while (true) {
                delay(1_000L)
                incrementCounter()
            }
        }
    }
}

class Factory(private val repository: CountryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CountryInfoViewModel(repository) as T
}




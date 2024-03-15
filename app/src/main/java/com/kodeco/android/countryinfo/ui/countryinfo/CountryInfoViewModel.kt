package com.kodeco.android.countryinfo.ui.countryinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.repositories.CountryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CountryInfoViewModel(private val repository: CountryRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<CountryInfoState> =
        MutableStateFlow(CountryInfoState.Loading)
    val uiState: StateFlow<CountryInfoState> = _uiState.asStateFlow()

    fun fetchCountries() {
        viewModelScope.launch {
            repository.fetchCountries()
                .catch { error ->
                    _uiState.value = CountryInfoState.Error(error)
                }
                .collect { list ->
                    _uiState.value = CountryInfoState.Success(list)
                }
        }
    }

    //move away from init block
    init {
        fetchCountries()
    }
}

class Factory(private val repository: CountryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CountryInfoViewModel(repository) as T
}




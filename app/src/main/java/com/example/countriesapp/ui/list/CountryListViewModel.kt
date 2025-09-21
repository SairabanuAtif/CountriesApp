package com.example.countriesapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countriesapp.data.model.CountryUiModel
import com.example.countriesapp.data.repository.CountryRepository
import com.example.countriesapp.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class CountryListViewModel(
    private val repository: CountryRepository
) : ViewModel() {

    private val _countries = MutableStateFlow<UiState<List<CountryUiModel>>>(UiState.Loading())
    val countries: StateFlow<UiState<List<CountryUiModel>>> = _countries.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _regionFilter = MutableStateFlow<String?>(null)
    val regionFilter: StateFlow<String?> = _regionFilter.asStateFlow()

    init {
        fetchCountries()
    }

    fun fetchCountries() {
        viewModelScope.launch {
            try {
                _countries.value = UiState.Loading()
                val data = repository.getCountries()
                _countries.value = UiState.Success(data)
            } catch (e: Exception) {
                _countries.value = UiState.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onRegionFilterChange(region: String?) {
        _regionFilter.value = region
    }

    fun getFilteredCountries(): List<CountryUiModel> {
        val allCountries = (_countries.value as? UiState.Success)?.data ?: emptyList()
        val filteredBySearch = allCountries.filter {
            it.name.contains(_searchQuery.value, ignoreCase = true)
        }
        return _regionFilter.value?.let { region ->
            filteredBySearch.filter { it.region.equals(region, ignoreCase = true) }
        } ?: filteredBySearch
    }
}
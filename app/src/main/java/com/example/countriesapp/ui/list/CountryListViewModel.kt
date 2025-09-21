package com.example.countriesapp.ui.list

import androidx.lifecycle.ViewModel
import com.example.countriesapp.data.model.CountryUiModel
import com.example.countriesapp.data.repository.CountryRepository
import com.example.countriesapp.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class CountryListViewModel(
    private val repository: CountryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<CountryUiModel>>>(UiState.Loading())
    val uiState: StateFlow<UiState<List<CountryUiModel>>> = _uiState.asStateFlow()

}
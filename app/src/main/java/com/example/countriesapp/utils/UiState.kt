package com.example.countriesapp.utils

sealed class UiState<T>(
    val data:T? = null,
    val message:String? = null
) {
//    object Loading : UiState()
//    data class Success(val countries: List<CountryUiModel>) : UiState()
//    data class Error(val message: String) : UiState()
    class Success<T>(data: T) : UiState<T>(data)
    class Error<T>(message: String, data: T? = null) : UiState<T>(data, message)
    class Loading<T>(data: T? = null) : UiState<T>(data)
}

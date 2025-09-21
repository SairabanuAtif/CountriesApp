package com.example.countriesapp.data.repository

import com.example.countriesapp.data.model.CountryUiModel

interface CountryRepository {
    suspend fun getCountries(): List<CountryUiModel>
}
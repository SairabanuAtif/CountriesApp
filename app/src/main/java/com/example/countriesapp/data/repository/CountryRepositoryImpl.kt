package com.example.countriesapp.data.repository

import com.example.countriesapp.data.api.CountryApiService
import com.example.countriesapp.data.model.CountryUiModel

class CountryRepositoryImpl(
    private val api: CountryApiService
): CountryRepository {
    override suspend fun getCountries(): List<CountryUiModel> {
        return api.getCountries().map { dto ->
            CountryUiModel(
                name = dto.name.common,
                region = dto.region,
                population = dto.population,
                flagUrl = dto.flags.png,
                capital = dto.capital?.firstOrNull() ?: "N/A",
                languages = dto.languages?.values?.joinToString(", ") ?: "N/A",
                currencies = dto.currencies?.values?.joinToString { "${it.name} (${it.symbol})" } ?: "N/A",
                timezones = dto.timezones.joinToString(", ")
            )
        }
    }
}
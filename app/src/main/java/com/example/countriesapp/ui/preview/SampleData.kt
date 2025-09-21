package com.example.countriesapp.ui.preview

import com.example.countriesapp.data.model.CountryUiModel

object SampleData {
    val countries = listOf(
        CountryUiModel(
            name = "India",
            capital = "New Delhi",
            region = "Asia",
            flagUrl = "https://restcountries.com/data/ind.png",
            population = 1_402_112_000,
            languages = "Hindi, English",
            currencies = "Indian Rupee (₹)",
            timezones = "UTC+05:30"
        ),
        CountryUiModel(
            name = "Germany",
            capital = "Berlin",
            region = "Europe",
            flagUrl = "https://restcountries.com/data/deu.png",
            population = 83_000_000,
            languages = "German",
            currencies = "Euro (€)",
            timezones = "UTC+01:00"
        ),
        CountryUiModel(
            name = "Brazil",
            capital = "Brasília",
            region = "Americas",
            flagUrl = "https://restcountries.com/data/bra.png",
            population = 213_000_000,
            languages = "Portuguese",
            currencies = "Brazilian Real (R$)",
            timezones = "UTC−03:00"
        )
    )
}
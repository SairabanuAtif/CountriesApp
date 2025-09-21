package com.example.countriesapp.data.api

data class CountryDto(
    val flags: Flags,
    val name: Name,
    val currencies: Map<String, Currency>,
    val capital: List<String>,
    val region: String?,
    val languages: Map<String, String>,
    val population: Long,
    val timezones: List<String>
)

data class Currency (
    val name: String,
    val symbol: String
)

data class Flags (
    val png: String,
    val svg: String,
    val alt: String
)

data class Name (
    val common: String,
    val official: String,
    val nativeName: Map<String, NativeName>
)

data class NativeName (
    val official: String,
    val common: String
)

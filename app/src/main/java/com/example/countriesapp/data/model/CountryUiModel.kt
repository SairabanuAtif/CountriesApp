package com.example.countriesapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryUiModel(
    val name: String,
    val capital: String,
    val region: String,
    val flagUrl: String,
    val population: Long,
    val languages: String,
    val currencies: String,
    val timezones: String
) : Parcelable

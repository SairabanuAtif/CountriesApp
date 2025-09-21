package com.example.countriesapp.utils

import java.text.NumberFormat
import java.util.Locale

fun formatNumber(number: Long): String {
    val formatter = NumberFormat.getInstance(Locale.US) // US = 1,000,000 format
    return formatter.format(number)
}
package com.example.countriesapp.ui.list

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.countriesapp.data.model.CountryUiModel

@Composable
fun CountryListScreen(
    onCountryClick: (CountryUiModel) -> Unit
){
    Text(text = "hello")
}
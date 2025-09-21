package com.example.countriesapp.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.countriesapp.R
import com.example.countriesapp.data.model.CountryUiModel
import com.example.countriesapp.ui.components.LoadImage
import com.example.countriesapp.ui.preview.SampleData
import com.example.countriesapp.ui.theme.Dimen
import com.example.countriesapp.ui.theme.Dimen.dimen16
import com.example.countriesapp.ui.theme.Dimen.dimen4
import com.example.countriesapp.ui.theme.Dimen.dimen8
import com.example.countriesapp.ui.theme.button_green

@Composable
fun CountryDetailsScreen(
    country: CountryUiModel,
    onBackClick: () -> Boolean
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(dimen8)
        ) {
            Text(
                text = stringResource(id = R.string.back),
                color = Color.White,
                modifier = Modifier
                    .clip(RoundedCornerShape(dimen8))
                    .background(button_green)
                    .clickable { onBackClick() }
                    .padding(vertical = dimen8, horizontal = dimen16),
                style = MaterialTheme.typography.bodySmall
            )
        }
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(dimen8))
            LoadImage(
                url = country.flagUrl,
                contentDescription = "${country.name} flag_detail",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(Dimen.dimen250)
            )

            Spacer(modifier = Modifier.height(dimen16))

            Column(modifier = Modifier.padding(horizontal = dimen16)) {
                DetailItem(
                    title = stringResource(id = R.string.capital_city),
                    value = country.capital
                )
                DetailItem(
                    title = stringResource(id = R.string.official_languages),
                    value = country.languages
                )
                DetailItem(
                    title = stringResource(id = R.string.currencies),
                    value = country.currencies
                )
                DetailItem(
                    title = stringResource(id = R.string.timezones),
                    value = country.timezones
                )
            }
        }
    }
}

@Composable
fun DetailItem(title: String, value: String) {
    Column(modifier = Modifier.padding(vertical = dimen8)) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(dimen4))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CountryDetailsScreenPreview() {
    CountryDetailsScreen(
        country = SampleData.countries[1],
        onBackClick = { true }
    )
}


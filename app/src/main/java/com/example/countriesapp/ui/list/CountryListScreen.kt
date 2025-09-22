package com.example.countriesapp.ui.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.countriesapp.R
import com.example.countriesapp.data.model.CountryUiModel
import com.example.countriesapp.ui.components.EmptyStateScreen
import com.example.countriesapp.ui.components.ErrorStateScreen
import com.example.countriesapp.ui.components.LoadImage
import com.example.countriesapp.ui.preview.SampleData
import com.example.countriesapp.ui.theme.Dimen.dimen1
import com.example.countriesapp.ui.theme.Dimen.dimen12
import com.example.countriesapp.ui.theme.Dimen.dimen16
import com.example.countriesapp.ui.theme.Dimen.dimen24
import com.example.countriesapp.ui.theme.Dimen.dimen32
import com.example.countriesapp.ui.theme.Dimen.dimen48
import com.example.countriesapp.ui.theme.Dimen.dimen56
import com.example.countriesapp.ui.theme.Dimen.dimen6
import com.example.countriesapp.ui.theme.Dimen.dimen60
import com.example.countriesapp.ui.theme.Dimen.dimen8
import com.example.countriesapp.ui.theme.semi_transparent_highlight
import com.example.countriesapp.utils.UiState
import com.example.countriesapp.utils.formatNumber


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryListScreen(
    countriesState: UiState<List<CountryUiModel>>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onRegionFilterChange: (String?) -> Unit,
    getFilteredCountries: () -> List<CountryUiModel>,
    onRetry: () -> Unit,
    onCountryClick: (CountryUiModel) -> Unit
) {
    var showRegionList by remember { mutableStateOf(false) }
    val customTextSelectionColors = TextSelectionColors(
        handleColor = MaterialTheme.colorScheme.onPrimary,
        backgroundColor = semi_transparent_highlight
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(MaterialTheme.colorScheme.primary),
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.app_icon),
                        contentDescription = stringResource(id = R.string.app_icon),
                        modifier = Modifier
                            .size(dimen24)
                            .padding(top = dimen1),
                    )
                },
                actions = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = stringResource(id = R.string.filter_icon),
                        modifier = Modifier
                            .size(dimen32)
                            .clickable(onClick = { showRegionList = !showRegionList })
                    )
                },
            )
        }, containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        when (countriesState) {
            is UiState.Loading ->
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                }

            is UiState.Error -> ErrorStateScreen(
                message = stringResource(id = R.string.Error_message),
                onRetry = { onRetry() }
            )

            is UiState.Success ->
                Box(modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(showRegionList) {
                        detectTapGestures {
                            if (showRegionList) {
                                showRegionList = false
                            }
                        }
                    }) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(dimen16)
                    ) {

                        CompositionLocalProvider(
                            LocalTextSelectionColors provides customTextSelectionColors
                        ) {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { onSearchQueryChange(it) },
                                placeholder = {
                                    Text(
                                        text = stringResource(id = R.string.search_countries),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(dimen1, Color.LightGray, RoundedCornerShape(dimen16)),
                                shape = RoundedCornerShape(dimen16),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    cursorColor = MaterialTheme.colorScheme.onPrimary,
                                    textColor = MaterialTheme.colorScheme.onPrimary,
                                    placeholderColor = Color.Gray
                                ),
                                singleLine = true
                            )
                        }
                        Spacer(modifier = Modifier.height(dimen16))
                        val filteredCountries = getFilteredCountries()

                        if (filteredCountries.isEmpty()) {
                            EmptyStateScreen(
                                message = stringResource(id = R.string.empty_screen_message),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(dimen32)
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = MaterialTheme.colorScheme.background)
                            ) {
                                items(filteredCountries, key = { it.name }) { country ->
                                    CountryListItem(
                                        country = country
                                    ) {
                                        onCountryClick(country)
                                    }
                                }
                            }
                        }
                    }
                    if (showRegionList) {
                        val regions = countriesState.data?.map { it.region }?.distinct()
                        RegionFilterList(
                            regions = regions,
                            onRegionSelected = { selectedRegion ->
                                onRegionFilterChange(selectedRegion)
                            },
                            onDismiss = { showRegionList = false }
                        )
                    }
                }
        }
    }
}

@Composable
fun CountryListItem(
    country: CountryUiModel,
    onCountryClick: (CountryUiModel) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimen6)
            .clickable { onCountryClick(country) },
        shape = RoundedCornerShape(dimen12),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(defaultElevation = dimen8)
    ) {
        Row(
            modifier = Modifier.padding(dimen16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LoadImage(
                url = country.flagUrl,
                contentDescription = "${country.name} flag_list",
                modifier = Modifier
                    .size(dimen60),
                isCircular = true
            )
            Spacer(modifier = Modifier.width(dimen16))
            Column {
                Text(
                    text = country.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Text(
                    text = stringResource(id = R.string.region_label) + country.region,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stringResource(
                        id = R.string.population_label,
                        formatNumber(country.population)
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun RegionFilterList(
    regions: List<String>?,
    onRegionSelected: (String?) -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(top = dimen56, start = dimen16, end = dimen16)
                .align(Alignment.TopEnd),
            shape = RoundedCornerShape(dimen12),
            elevation = CardDefaults.cardElevation(dimen8),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.all),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onRegionSelected(null)
                            onDismiss()
                        }
                        .padding(dimen12),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                regions?.forEach { region ->
                    Text(
                        text = region,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onRegionSelected(region)
                                onDismiss()
                            }
                            .padding(dimen12),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun CountryListScreenPreview() {
    CountryListScreen(
        countriesState = UiState.Success(SampleData.countries),
        searchQuery = "",
        onSearchQueryChange = {},
        onRegionFilterChange = {},
        getFilteredCountries = { SampleData.countries },
        onRetry = {},
        onCountryClick = {}
    )
}

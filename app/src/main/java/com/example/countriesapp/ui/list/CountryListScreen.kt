package com.example.countriesapp.ui.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.ImageSource
import coil.request.ImageRequest
import com.example.countriesapp.R
import com.example.countriesapp.data.model.CountryUiModel
import com.example.countriesapp.utils.UiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryListScreen(
    countriesState: UiState<List<CountryUiModel>>,
    searchQuery: String,
    regionFilter: String?,
    onSearchQueryChange: (String) -> Unit,
    onRegionFilterChange: (String?) -> Unit,
    getFilteredCountries: () -> List<CountryUiModel>,
    onCountryClick: (CountryUiModel) -> Unit
) {
    var showRegionList by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontSize = 24.sp
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(Color.White),
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.app_icon),
                        contentDescription = "",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(top = 1.dp),
                    )
                },
                actions = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = "",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable(onClick = { showRegionList = !showRegionList })
                    )
                },
            )
        }, containerColor = Color.White
    ) { paddingValues ->
        when (countriesState) {
            is UiState.Loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            is UiState.Error -> Text(
                text = countriesState.message ?: "Error",
                color = Color.Red
            )

            is UiState.Success ->
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(16.dp)
                    ) {

                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { onSearchQueryChange(it) },
                            placeholder = {
                                Text(
                                    text = "Search countries",
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp)),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = Color.White)
                        ) {
                            items(getFilteredCountries(), key = { it.name }) { country ->
                                CountryListItem(
                                    country = country
                                ) {
                                    onCountryClick(country)
                                }
                            }
                        }
                    }
                    if (showRegionList) {
                        val regions = countriesState.data?.mapNotNull { it.region }?.distinct()
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
            .padding(vertical = 6.dp)
            .clickable { onCountryClick(country) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(country.flagUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "${country.name} flag",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = country.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Region: ${country.region}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Population: ${country.population}",
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
                .padding(top = 56.dp, start = 16.dp, end = 16.dp)
                .align(Alignment.TopEnd),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column {
                Text(
                    text = "All",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onRegionSelected(null)
                            onDismiss()
                        }
                        .padding(12.dp)
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
                            .padding(12.dp)
                    )
                }
            }
        }
    }
}

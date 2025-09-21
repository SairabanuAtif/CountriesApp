package com.example.countriesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.countriesapp.data.api.CountryApiService
import com.example.countriesapp.data.model.CountryUiModel
import com.example.countriesapp.data.repository.CountryRepositoryImpl
import com.example.countriesapp.ui.details.CountryDetailsScreen
import com.example.countriesapp.ui.list.CountryListScreen
import com.example.countriesapp.ui.list.CountryListViewModel
import com.example.countriesapp.ui.components.EmptyStateScreen
import com.example.countriesapp.ui.theme.CountriesAppTheme
import com.example.countriesapp.ui.theme.Dimen.dimen32

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<CountryListViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val repository = CountryRepositoryImpl(CountryApiService.create())
                    return CountryListViewModel(repository) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountriesAppTheme {
                val navController = rememberNavController()

                val countriesState by viewModel.countries.collectAsState()
                val searchQuery by viewModel.searchQuery.collectAsState()

                NavHost(navController = navController, startDestination = "list") {
                    composable("list") {
                        CountryListScreen(
                            countriesState = countriesState,
                            searchQuery = searchQuery,
                            onSearchQueryChange = { query -> viewModel.onSearchQueryChange(query) },
                            onRegionFilterChange = { region -> viewModel.onRegionFilterChange(region) },
                            getFilteredCountries = { viewModel.getFilteredCountries() },
                            onRetry = { viewModel.fetchCountries() },
                            onCountryClick = { country ->
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "country",
                                    country
                                )
                                navController.navigate("details")
                            }
                        )
                    }

                    composable("details") {
                        val country =
                            navController.previousBackStackEntry?.savedStateHandle?.get<CountryUiModel>(
                                "country"
                            )

                        if (country == null) {
                            EmptyStateScreen(
                                message = stringResource(id = R.string.no_countries_found),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(dimen32)
                            )
                        } else {
                            CountryDetailsScreen(
                                country = country
                            ) { navController.popBackStack() }
                        }
                    }
                }
            }
        }
    }
}



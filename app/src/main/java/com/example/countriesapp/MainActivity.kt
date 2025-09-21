package com.example.countriesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.countriesapp.ui.theme.CountriesAppTheme
import com.example.countriesapp.utils.UiState

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
                CountriesAppTheme() {
                val navController = rememberNavController()

                // Collect state here
                val countriesState by viewModel.countries.collectAsState()
                val searchQuery by viewModel.searchQuery.collectAsState()
                val regionFilter by viewModel.regionFilter.collectAsState()

                NavHost(navController = navController, startDestination = "list") {
                    composable("list") {
                        CountryListScreen(
                            countriesState = countriesState,
                            searchQuery = searchQuery,
                            regionFilter = regionFilter,
                            onSearchQueryChange = { query -> viewModel.onSearchQueryChange(query) },
                            onRegionFilterChange = { region -> viewModel.onRegionFilterChange(region) },
                            getFilteredCountries = { viewModel.getFilteredCountries() },
                            onCountryClick = { country ->
                                navController.currentBackStackEntry?.arguments?.putParcelable(
                                    "country",
                                    country
                                )
                                navController.navigate("details")
                            }
                        )
                    }

                    composable("details") {
                        val country =
                            navController.previousBackStackEntry?.arguments?.getParcelable<CountryUiModel>(
                                "country"
                            )
//                    country?.let { CountryDetailsScreen(it) }
                    }
                }
            }
        }
    }
}

//@Composable
//private fun CountriesUi(countriesState : ) {
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = "list"){
//        composable("list"){
//            CountryListScreen{ country ->
//                navController.currentBackStackEntry?.arguments?.putParcelable("country",country)
//                navController.navigate("details")
//            }
//        }
//        composable("details"){
//            val country = navController.previousBackStackEntry?.arguments?.getParcelable<CountryUiModel>("country")
//            country?.let{
////                CountryDetailsScreen(it)
//            }
//        }
//    }
//}



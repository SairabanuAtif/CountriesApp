package com.example.countriesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
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

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: CountryListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = CountryRepositoryImpl(CountryApiService.create())
        viewModel = CountryListViewModel(repository)
        setContent {
            CountriesAppTheme {
                CountriesUi()

            }
        }
    }
}

@Composable
private fun CountriesUi() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "list"){
        composable("list"){
            CountryListScreen{ country ->
                navController.currentBackStackEntry?.arguments?.putParcelable("country",country)
                navController.navigate("details")
            }
        }
        composable("details"){
            val country = navController.previousBackStackEntry?.arguments?.getParcelable<CountryUiModel>("country")
            country?.let{
//                CountryDetailsScreen(it)
            }
        }
    }
}



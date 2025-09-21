package com.example.countriesapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface CountryApiService {
    @GET("all?fields=name,capital,region,flags,population,languages,currencies,timezones")
    suspend fun getCountries(): List<CountryDto>

    companion object {
        private const val BASE_URL = "https://restcountries.com/v3.1/"

        fun create(): CountryApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            return retrofit.create(CountryApiService::class.java)
        }
    }
}
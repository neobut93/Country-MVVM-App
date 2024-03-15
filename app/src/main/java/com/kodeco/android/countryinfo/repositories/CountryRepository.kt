package com.kodeco.android.countryinfo.repositories

import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.models.CountryName
import kotlinx.coroutines.flow.Flow

interface CountryRepository {

    fun fetchCountries(): Flow<List<Country>>

    fun getCountry(name: CountryName): Country?
}
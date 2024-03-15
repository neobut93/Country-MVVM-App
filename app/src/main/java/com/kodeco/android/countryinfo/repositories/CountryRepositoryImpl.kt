package com.kodeco.android.countryinfo.repositories

import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.models.CountryName
import com.kodeco.android.countryinfo.network.CountryService
import com.kodeco.android.countryinfo.ui.countryinfo.CountryInfoState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Response

class CountryRepositoryImpl(private val service: CountryService) : CountryRepository {

    private val countries: List<Country> = emptyList()

    override fun fetchCountries(): Flow<List<Country>> = flow {
        val countriesResponse = service.getAllCountries()
        val countryList: List<Country>  = if (countriesResponse.isSuccessful) {
            countriesResponse.body()!!
        } else {
            countries
        }
        emit(countryList)
    }

    override fun getCountry(name: CountryName): Country? {
        lateinit var country: Country
        for (i in 0..countries.lastIndex) {
            if (countries[i].commonName==name.toString()) {
                country = countries[i]
            }
        }
        return country
    }
}
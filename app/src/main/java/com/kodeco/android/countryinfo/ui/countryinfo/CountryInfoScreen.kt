package com.kodeco.android.countryinfo.ui.countryinfo

import android.os.Parcelable
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.ui.components.CountryInfoList
import com.kodeco.android.countryinfo.ui.components.Error
import com.kodeco.android.countryinfo.ui.components.Loading
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class CountryInfoState : Parcelable {
    data object Loading : CountryInfoState()
    data class Success(val countries: List<Country>) : CountryInfoState()
    data class Error(val error: Throwable) : CountryInfoState()
}

@Composable
fun CountryInfoScreen(
    viewModel: CountryInfoViewModel
) {
    val state = viewModel.uiState.collectAsState()
    Surface {
        when (val countryInfoState = state.value) {
            is CountryInfoState.Loading -> Loading(
                viewModel = viewModel
            )
            is CountryInfoState.Success -> CountryInfoList(
                countries = countryInfoState.countries,

                //todo solve issue with refresh
                onRefreshClick = viewModel::fetchCountries,
                viewModel = viewModel
            )

            is CountryInfoState.Error -> Error(error = countryInfoState.error) {

            }

        }
    }
}

// TODO: Move this logic in to the viewmodel.


@Preview
@Composable
fun CountryInfoScreenPreview() {
//    CountryInfoScreen(
//        service = object : CountryService {
//            override suspend fun getAllCountries(): Response<List<Country>> =
//                Response.success(sampleCountries)
//        },
//    )
}

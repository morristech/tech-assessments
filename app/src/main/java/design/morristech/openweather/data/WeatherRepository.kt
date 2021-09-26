package design.morristech.openweather.data

import design.morristech.openweather.core.Result
import design.morristech.openweather.data.local.model.FavouriteForecast
import design.morristech.openweather.data.local.model.Forecast
import design.morristech.openweather.data.local.model.Location
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getForecastById(id: Int): Result<Forecast>

    suspend fun getForecastByLocation(location: Location): Result<Forecast>

    suspend fun getUpdatedForecast(forecast: Forecast): Result<Forecast>

    suspend fun getObservableCurrentForecast(): Flow<Result<Forecast>>

    suspend fun getFavouriteForecasts(): Result<List<FavouriteForecast>>

    suspend fun updateFavouriteForecasts()

    suspend fun starEvent(forecast: Forecast, isStarred: Boolean)

    suspend fun isEmpty(): Boolean
}

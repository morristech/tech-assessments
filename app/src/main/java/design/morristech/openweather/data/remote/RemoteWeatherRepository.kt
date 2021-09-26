package design.morristech.openweather.data.remote

import design.morristech.openweather.core.Result
import design.morristech.openweather.core.exceptions.DataNotFound
import design.morristech.openweather.data.AppSettings
import design.morristech.openweather.data.WeatherDataSource
import design.morristech.openweather.data.WeatherRepository
import design.morristech.openweather.data.db.WeatherDataBase
import design.morristech.openweather.data.local.model.FavouriteForecast
import design.morristech.openweather.data.local.model.Forecast
import design.morristech.openweather.data.local.model.Location
import design.morristech.openweather.utils.extensions.toFavouriteForecast
import design.morristech.openweather.utils.extensions.toLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteWeatherRepository @Inject constructor(
    private val weatherDataSource: WeatherDataSource,
    private val database: WeatherDataBase,
    private val settings: AppSettings
) : WeatherRepository {

    override suspend fun getForecastById(id: Int): Result<Forecast> =
        withContext(Dispatchers.Default) {
            // Since id is a parameter, forecast must be in database
            val forecast: Forecast = database.getForecastDao().getForecasts().find { it.id == id }!!

            // Changing displayed (current) forecast on the main screen
            database.getForecastDao().updateCurrentForecast(forecast.locationName)

            // If data is outdated, then requesting new forecast
            if (forecast.isOutdated) {
                return@withContext weatherDataSource
                    .request(forecast.toLocation(), settings.locale)
                    .onSuccess {
                        it.isFavourite = forecast.isFavourite
                        it.id = forecast.id
                        database.getForecastDao().updateForecast(it)
                    }
            }
            return@withContext Result.Success(forecast)
        }

    override suspend fun getForecastByLocation(location: Location): Result<Forecast> =
        withContext(Dispatchers.Default) {
            // Forecast can be null if user chose new location (in search)
            val forecast: Forecast? = database.getForecastDao().getForecasts()
                .find { it.locationName == location.locationName }

            // Changing displayed (current) forecast on the main screen
            database.getForecastDao().updateCurrentForecast(location.locationName)
            if (forecast != null) {
                if (!forecast.isOutdated) {
                    return@withContext Result.Success(forecast)
                } else {
                    return@withContext getUpdatedForecast(forecast)
                }
            } else {
                return@withContext weatherDataSource
                    .request(location, settings.locale)
                    .onSuccess { database.getForecastDao().insertForecast(it) }
            }
        }

    /**
     * Returns result of requesting new forecast by given old [forecast] information
     */
    override suspend fun getUpdatedForecast(forecast: Forecast): Result<Forecast> {
        return weatherDataSource
            .request(forecast.toLocation(), settings.locale)
            .onSuccess {
                it.id = forecast.id
                it.isFavourite = forecast.isFavourite
                database.getForecastDao().updateForecast(it)
            }
    }

    /**
     * Observable result of getting current forecast
     *
     * At first sends cached forecast, and then if it is outdated sends updated one
     */
    override suspend fun getObservableCurrentForecast(): Flow<Result<Forecast>> = flow {
        val currentForecast = database.getForecastDao().getCurrentForecast()
        if (currentForecast != null) {
            emit(Result.Success(currentForecast))
            if (currentForecast.isOutdated) {
                emit(getUpdatedForecast(currentForecast))
            }
        } else {
            emit(Result.Error(DataNotFound))
        }
    }

    /**
     * Checks if database contains any data
     */
    override suspend fun isEmpty(): Boolean = database.getForecastDao().getForecasts().isEmpty()

    /**
     *  Returns result of getting favourite locations from database
     */
    override suspend fun getFavouriteForecasts(): Result<List<FavouriteForecast>> =
        withContext(Dispatchers.Default) {
            val favouriteForecasts = database.getForecastDao().getFavouriteForecasts()
            return@withContext if (favouriteForecasts.isNotEmpty()) {
                Result.Success(favouriteForecasts.map(Forecast::toFavouriteForecast))
            } else {
                Result.Error(DataNotFound)
            }
        }

    /**
     * Updates forecast for favourite locations asynchronously
     */
    override suspend fun updateFavouriteForecasts() = coroutineScope {
        val favouriteForecasts: List<Forecast> = database.getForecastDao().getFavouriteForecasts()
        for (oldForecast in favouriteForecasts) {
            if (oldForecast.isOutdated) {
                launch {
                    getUpdatedForecast(oldForecast)
                }
            }
        }
    }

    /**
     * Changes forecast favourite status (isFavourite) in database
     */
    override suspend fun starEvent(forecast: Forecast, isStarred: Boolean) {
        database.getForecastDao().setForecastFavouriteStatus(forecast.locationName, isStarred)
    }
}

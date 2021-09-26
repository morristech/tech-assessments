package design.morristech.openweather.data.remote

import design.morristech.openweather.core.Result
import design.morristech.openweather.data.WeatherDataSource
import design.morristech.openweather.data.local.model.*
import design.morristech.openweather.data.remote.model.Current
import design.morristech.openweather.data.remote.model.Daily
import design.morristech.openweather.data.remote.model.Hourly
import design.morristech.openweather.data.remote.model.WeatherResponse
import design.morristech.openweather.utils.extensions.toCapitalizedDay
import design.morristech.openweather.utils.extensions.toDayMonth
import design.morristech.openweather.utils.extensions.toHourMinutes
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

class RemoteWeatherDataSource @Inject constructor(
    val weatherApi: WeatherApi
) : WeatherDataSource {

    companion object {
        // OpenWeatherMap API default constants
        private const val DEFAULT_UNITS = "metric"
        private const val HOURLY_COUNT = 24
        private const val DAILY_COUNT = 5
    }

    override suspend fun request(location: Location, locale: Locale): Result<Forecast> =
        safeApiCall(
            call = {
                weatherApi.getForecast(
                    longitude = location.longitude,
                    latitude = location.latitude,
                    language = locale.language,
                    units = DEFAULT_UNITS
                )
            },
            params = locale,
            transform = ::transformToForecast
        ).onSuccess { it.locationName = location.locationName }

    /**
     * Converts [WeatherResponse] to the [Forecast]
     */
    private fun transformToForecast(response: WeatherResponse, locale: Locale) = Forecast(
        locationName = response.timezone, // temporary name that will be changed
        longitude = response.lon,
        latitude = response.lat,
        currentForecast = response.current.toCurrentForecast(locale),
        hourForecastList = response.hourly.toHourList(locale),
        dayForecastList = response.daily.toDayList(locale),
    )

    private fun Current.toCurrentForecast(locale: Locale) =
        CurrentForecast(
            temperature = Temperature(temp.roundToInt()),
            feelsLike = Temperature(feelsLike.roundToInt()),
            description = weather[0].description.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    locale
                ) else it.toString()
            },
            wind = Wind(windSpeed.roundToInt()),
            humidity = humidity,
            pressure = Pressure(pressure),
            imageId = getIconByCode(weather[0].id)
        )

    private fun List<Hourly>.toHourList(locale: Locale) = take(HOURLY_COUNT).map {
        HourForecast(
            temperature = Temperature(it.temperature.roundToInt()),
            time = it.timestamp.toHourMinutes(locale),
            imageId = getIconByCode(it.weather[0].id)
        )
    }

    private fun List<Daily>.toDayList(locale: Locale) = take(DAILY_COUNT).map {
        DayForecast(
            highestTemp = Temperature(it.temperature.max.roundToInt()),
            lowestTemp = Temperature(it.temperature.min.roundToInt()),
            day = it.timestamp.toCapitalizedDay(locale),
            date = it.timestamp.toDayMonth(locale),
            wind = Wind(it.windSpeed.roundToInt()),
            pressure = Pressure(it.pressure),
            humidity = it.humidity,
            imageId = getIconByCode(it.weather[0].id)
        )
    }
}

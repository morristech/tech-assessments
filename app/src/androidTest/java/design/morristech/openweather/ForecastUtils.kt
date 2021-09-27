package design.morristech.openweather

import design.morristech.openweather.data.local.model.*
import java.util.*

/**
 * [Forecast] object used for tests
 */
val fakeForecast = Forecast(
    id = -1,
    locationName = "Moscow, Central Administrative Okrug, Russia",
    longitude = 37.6174943,
    latitude = 55.7504461,
    timestamp = Calendar.getInstance(),
    wasOpenedLast = false,
    isFavourite = false,
    currentForecast = CurrentForecast(
        currentForecastId = 0,
        temperature = Temperature(21),
        description = "Broken clouds",
        feelsLike = Temperature(18),
        wind = Wind(5),
        pressure = Pressure(1022),
        humidity = 56,
        imageId = 2131230822
    ),
    hourForecastList = arrayListOf(
        HourForecast(0, Temperature(21), "15:00", 1),
        HourForecast(1, Temperature(19), "18:00", 2),
        HourForecast(2, Temperature(16), "21:00", 3),
        HourForecast(3, Temperature(15), "23:00", 4),
    ),
    dayForecastList = arrayListOf(
        DayForecast(
            Temperature(21),
            Temperature(16),
            "Thursday",
            "25.06",
            Wind(3),
            Pressure(5),
            97,
            R.drawable.rain
        ),
        DayForecast(
            Temperature(20),
            Temperature(15),
            "Friday",
            "26.06",
            Wind(3),
            Pressure(5),
            97,
            R.drawable.partly_sunny
        ),
        DayForecast(
            Temperature(23),
            Temperature(17),
            "Saturday",
            "27.06",
            Wind(3),
            Pressure(5),
            97,
            R.drawable.clear
        ),
        DayForecast(
            Temperature(17),
            Temperature(9),
            "Sunday",
            "28.06",
            Wind(3),
            Pressure(5),
            97,
            R.drawable.rain
        ),
        DayForecast(
            Temperature(10),
            Temperature(3),
            "Monday",
            "29.06",
            Wind(3),
            Pressure(5),
            97,
            R.drawable.partly_sunny
        ),
        DayForecast(
            Temperature(22),
            Temperature(16),
            "Tuesday",
            "30.06",
            Wind(3),
            Pressure(5),
            97,
            R.drawable.partly_sunny
        ),
        DayForecast(
            Temperature(22),
            Temperature(17),
            "Wednesday",
            "01.07",
            Wind(3),
            Pressure(5),
            97,
            R.drawable.rain
        )
    )
)

/**
 * Cape Town [Forecast] object used for tests
 */
val capeTownForecast = fakeForecast.copy(
    id = 1,
    locationName = "Cape Town, South Africa",
    latitude = 33.9249,
    longitude = 18.424,
    wasOpenedLast = false,
    isFavourite = false
)

/**
 * Johannesburg [Forecast] object used for tests
 */
val joburgForecast = fakeForecast.copy(
    id = 2,
    locationName = "Johannesburg, South Africa",
    latitude = 26.2041,
    longitude = 28.0473,
    wasOpenedLast = true,
    isFavourite = true
)

/**
 * Amsterdam [Forecast] object used for tests
 */
val amsterdamForecast = fakeForecast.copy(
    id = 3,
    locationName = "Amsterdam, Holland",
    latitude = 52.3676,
    longitude = 4.9041,
    wasOpenedLast = false,
    isFavourite = true
)

/**
 * List of forecasts used for tests
 */
val forecasts = listOf(
    capeTownForecast,
    joburgForecast,
    amsterdamForecast
)

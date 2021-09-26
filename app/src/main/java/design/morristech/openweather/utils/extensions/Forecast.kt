package design.morristech.openweather.utils.extensions

import design.morristech.openweather.data.local.model.FavouriteForecast
import design.morristech.openweather.data.local.model.Forecast
import design.morristech.openweather.data.local.model.Location

fun Forecast.toLocation() = Location(locationName, longitude, latitude)

fun Forecast.toFavouriteForecast() = FavouriteForecast(
    id = id,
    temperature = currentForecast.temperature,
    description = currentForecast.description,
    locationName = locationName,
    imageId = currentForecast.imageId
)

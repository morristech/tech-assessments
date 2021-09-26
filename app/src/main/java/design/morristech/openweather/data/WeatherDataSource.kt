package design.morristech.openweather.data

import design.morristech.openweather.core.NetworkDataSource
import design.morristech.openweather.core.Result
import design.morristech.openweather.data.local.model.Forecast
import design.morristech.openweather.data.local.model.Location
import java.util.*

interface WeatherDataSource: NetworkDataSource {
    suspend fun request(location: Location, locale: Locale): Result<Forecast>
}

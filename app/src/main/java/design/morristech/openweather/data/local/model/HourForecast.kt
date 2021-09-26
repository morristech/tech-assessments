package design.morristech.openweather.data.local.model

import androidx.annotation.DrawableRes
import androidx.annotation.Keep

@Keep
data class HourForecast(
    var currentForecastId: Int = 0,
    var temperature: Temperature,
    val time: String,
    @DrawableRes val imageId: Int
)

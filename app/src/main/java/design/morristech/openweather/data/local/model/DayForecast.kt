package design.morristech.openweather.data.local.model

import androidx.annotation.DrawableRes
import androidx.annotation.Keep

@Keep
data class DayForecast(
    var highestTemp: Temperature,
    var lowestTemp: Temperature,
    val day: String,
    val date: String,
    val wind: Wind,
    val pressure: Pressure,
    val humidity: Int,
    @DrawableRes val imageId: Int
)

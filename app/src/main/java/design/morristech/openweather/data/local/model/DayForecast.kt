package design.morristech.openweather.data.local.model

import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import androidx.room.TypeConverters
import design.morristech.openweather.data.local.db.Converters

@Keep
data class DayForecast(
    @TypeConverters(Converters::class)
    var highestTemp: Temperature,
    @TypeConverters(Converters::class)
    var lowestTemp: Temperature,
    val day: String,
    val date: String,
    @TypeConverters(Converters::class)
    val wind: Wind,
    @TypeConverters(Converters::class)
    val pressure: Pressure,
    val humidity: Int,
    @DrawableRes val imageId: Int
)

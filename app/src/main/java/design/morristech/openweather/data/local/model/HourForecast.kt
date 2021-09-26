package design.morristech.openweather.data.local.model

import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import androidx.room.TypeConverters
import design.morristech.openweather.data.db.Converters

@Keep
data class HourForecast(
    var currentForecastId: Int = 0,
    @TypeConverters(Converters::class)
    var temperature: Temperature,
    val time: String,
    @DrawableRes val imageId: Int
)
